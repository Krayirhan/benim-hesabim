package com.benimhesabim.app.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.benimhesabim.app.core.common.AmountFormatter
import com.benimhesabim.app.core.common.DateFormatter
import com.benimhesabim.app.domain.model.TransactionType

@Composable
fun HomeScreen(
    onAddTransaction: () -> Unit,
    onOpenTransactions: () -> Unit,
    onOpenSettings: () -> Unit,
    onLogout: () -> Unit,
    viewModel: HomeViewModel
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Benim Hesabım") },
                actions = {
                    IconButton(onClick = onOpenTransactions) {
                        Icon(Icons.Default.List, contentDescription = "İşlemler")
                    }
                    IconButton(onClick = onOpenSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Ayarlar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTransaction) {
                Icon(Icons.Default.Add, contentDescription = "Yeni işlem")
            }
        }
    ) { padding ->
        if (state.isLoading) {
            Column(modifier = Modifier.fillMaxSize().padding(padding), verticalArrangement = Arrangement.Center) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            contentPadding = PaddingValues(bottom = 88.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text("Bu Ay", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                SummaryCard("Bu Ay Gelir", AmountFormatter.formatMinor(state.summary.totalIncomeMinor))
                Spacer(modifier = Modifier.height(8.dp))
                SummaryCard("Bu Ay Gider", AmountFormatter.formatMinor(state.summary.totalExpenseMinor))
                Spacer(modifier = Modifier.height(8.dp))
                SummaryCard("Kalan Bakiye", AmountFormatter.formatMinor(state.summary.balanceMinor))
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Son İşlemler", style = MaterialTheme.typography.titleLarge)
            }
            items(state.recentTransactions) { transaction ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(transaction.title)
                            Text("${transaction.category} • ${DateFormatter.display(transaction.transactionDate)}")
                        }
                        Text(
                            if (transaction.type == TransactionType.INCOME) {
                                "+${AmountFormatter.formatMinor(transaction.amountMinor)}"
                            } else {
                                "-${AmountFormatter.formatMinor(transaction.amountMinor)}"
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SummaryCard(title: String, value: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.labelLarge)
            Text(value, style = MaterialTheme.typography.headlineSmall)
        }
    }
}
