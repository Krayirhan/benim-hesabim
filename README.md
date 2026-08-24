<div align="center">

# 💸 Benim Hesabım

### Gelir / Gider Takibi — Android

*Offline-first Room + Supabase senkronizasyonu · MVVM*

[![Kotlin](https://img.shields.io/badge/Kotlin-Jetpack%20Compose-7F52FF?style=flat-square&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Supabase](https://img.shields.io/badge/Supabase-Auth%20%2B%20Postgres-3ECF8E?style=flat-square&logo=supabase&logoColor=white)](https://supabase.com)
[![Room](https://img.shields.io/badge/Room-Offline--first-3DDC84?style=flat-square&logo=android&logoColor=white)](https://developer.android.com/training/data-storage/room)
[![Hilt](https://img.shields.io/badge/DI-Hilt-4285F4?style=flat-square&logo=dagger&logoColor=white)](https://dagger.dev/hilt/)
[![minSdk](https://img.shields.io/badge/minSdk-26-brightgreen?style=flat-square)](app/build.gradle.kts)

</div>

---

Kullanıcının gelir/gider hareketlerini hızlıca kaydedip aylık durumunu görebildiği bir Android MVP'si. Önce **Room** ile tamamen offline çalışır, ardından **Supabase** (Auth + PostgreSQL) ile senkronize olacak şekilde tasarlanmıştır.

## ✨ Özellikler

| Ekran | Açıklama |
|---|---|
| `SplashScreen` | Oturum kontrolü ve ilk yönlendirme |
| `LoginScreen` / `RegisterScreen` | Email/şifre ile giriş ve kayıt |
| `HomeScreen` | Toplam gelir, gider ve kalan bakiye özeti |
| `AddTransactionScreen` | Yeni gelir/gider ekleme |
| `TransactionListScreen` | Tüm işlemlerin listesi |
| `SettingsScreen` | Hesap bilgisi ve çıkış |

**Planlanan:** `TransactionDetailScreen` · `EditTransactionScreen` · `ProfileScreen` · `SyncStatusScreen` · `AboutScreen`

## 🧰 Teknoloji Yığını

Kotlin · Jetpack Compose · Material 3 · MVVM · Hilt · Room · Coroutines + Flow · Navigation Compose · Supabase Auth · Supabase PostgreSQL · Gradle Version Catalog

## 📁 Proje Yapısı

```
com.benimhesabim.app/
├── core/            common · designsystem · util · navigation
├── data/
│   ├── local/       database · dao · entity (Room)
│   ├── remote/      supabase · dto
│   ├── repository/
│   └── mapper/
├── domain/          model · repository (arayüz) · usecase
├── feature/         auth · home · transaction · settings
└── app/             MainActivity · BenimHesabimApplication
```

## 🚀 Kurulum

### 1 — Supabase projesi oluştur

Supabase Dashboard'da yeni proje oluşturun, ardından `supabase/schema.sql` içeriğini SQL Editor'de çalıştırın.

### 2 — Ortam değişkenlerini ayarla

`local.properties` dosyasına (`local.properties.example` referans alınabilir):

```properties
SUPABASE_URL=<supabase-project-url>
SUPABASE_ANON_KEY=<supabase-anon-key>
```

> ⚠️ Sadece `anon` key kullanın — `service_role` key'i mobil uygulamaya asla koymayın. Gerçek `local.properties` ve `.env` dosyaları `.gitignore` ile hariç tutulur.

### 3 — Çalıştır

```bash
git clone https://github.com/Krayirhan/benim-hesabim.git
cd benim-hesabim
# Android Studio ile aç → Gradle sync → Run
```

---

<div align="center">

**Stack:** Kotlin · Jetpack Compose · Room · Hilt · Supabase

</div>
