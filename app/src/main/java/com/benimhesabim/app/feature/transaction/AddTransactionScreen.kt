package com.benimhesabim.app.feature.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.benimhesabim.app.core.common.TransactionCategories
import com.benimhesabim.app.core.common.DateFormatter
import com.benimhesabim.app.domain.model.TransactionType
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    onBack: () -> Unit,
    onSaved: () -> Unit,
    viewModel: AddTransactionViewModel
) {
    val state by viewModel.uiState.collectAsState()
    var showDatePicker by remember { mutableStateOf(false) }
    val pickerState = rememberDatePickerState(
        initialSelectedDateMillis = state.date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    )

    LaunchedEffect(state.saved) {
        if (state.saved) {
            viewModel.clearSaved()
            onSaved()
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val millis = pickerState.selectedDateMillis
                    if (millis != null) {
                        viewModel.onDateChange(Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate())
                    }
                    showDatePicker = false
                }) { Text("Tamam") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("İptal") }
            }
        ) {
            DatePicker(state = pickerState)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Yeni İşlem", style = MaterialTheme.typography.headlineSmall)
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            SegmentedButton(
                selected = state.type == TransactionType.INCOME,
                onClick = { viewModel.onTypeChange(TransactionType.INCOME) },
                shape = androidx.compose.material3.SegmentedButtonDefaults.itemShape(0, 2)
            ) {
                Text("Gelir")
            }
            SegmentedButton(
                selected = state.type == TransactionType.EXPENSE,
                onClick = { viewModel.onTypeChange(TransactionType.EXPENSE) },
                shape = androidx.compose.material3.SegmentedButtonDefaults.itemShape(1, 2)
            ) {
                Text("Gider")
            }
        }
        OutlinedTextField(
            value = state.title,
            onValueChange = viewModel::onTitleChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Başlık") }
        )
        OutlinedTextField(
            value = state.amountText,
            onValueChange = viewModel::onAmountChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Tutar") },
            supportingText = { Text("Örn: 125,50") }
        )
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            val categories = if (state.type == TransactionType.INCOME) TransactionCategories.income else TransactionCategories.expense
            categories.forEachIndexed { index, category ->
                SegmentedButton(
                    selected = state.category == category,
                    onClick = { viewModel.onCategoryChange(category) },
                    shape = androidx.compose.material3.SegmentedButtonDefaults.itemShape(index, categories.size)
                ) {
                    Text(category)
                }
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Tarih: ${DateFormatter.display(state.date)}")
            TextButton(onClick = { showDatePicker = true }) { Text("Değiştir") }
        }
        OutlinedTextField(
            value = state.note,
            onValueChange = viewModel::onNoteChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Not") }
        )
        Button(
            onClick = viewModel::save,
            enabled = !state.isSaving,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Kaydet")
        }
        state.errorMessage?.let { Text(it) }
    }
}
