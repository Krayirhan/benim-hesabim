# Benim Hesabım

Basit ama düzenli mimarili bir Android MVP.

Amaç:
- Gelir/gider ekleme
- İşlemleri listeleme
- Bu ayki gelir, gider ve kalan bakiyeyi görme
- Supabase Auth ile email/şifre giriş ve kayıt
- Room ile offline-first çalışma

## Kullanılan Teknolojiler
- Kotlin
- Jetpack Compose
- Material 3
- MVVM
- Hilt
- Room
- Coroutines + Flow
- Navigation Compose
- Supabase Auth
- Supabase PostgreSQL
- Gradle Version Catalog

## Supabase Kurulumu
1. Supabase Dashboard'da proje oluşturun.
2. `supabase/schema.sql` dosyasındaki SQL'i Supabase SQL Editor içinde çalıştırın.
3. Project Settings > API bölümünden sadece `anon` key alın.
4. `service_role` key'i mobil uygulamaya koymayın.

## `local.properties` İçeriği
`local.properties` dosyasına şunları ekleyin:

```properties
SUPABASE_URL=BURAYA_SUPABASE_PROJECT_URL_GIR
SUPABASE_ANON_KEY=BURAYA_SUPABASE_ANON_KEY_GIR
```

Gerçek değerleri Supabase Dashboard > Project Settings > API bölümünden alın.
`service_role` key kullanmayın.

## `local.properties.example` ve `.env.example`
Bu dosyalar repoya eklendi:
- `.env.example`
- `local.properties.example`

Gerçek `local.properties` ve `.env` dosyaları `.gitignore` içindedir.

## Supabase SQL Dosyasını Çalıştırma
1. Supabase Dashboard açın.
2. SQL Editor bölümüne gidin.
3. `supabase/schema.sql` içeriğini yapıştırın.
4. Çalıştırın.

## Çalıştırma Adımları
1. Android Studio ile projeyi açın.
2. `local.properties` dosyasını oluşturun ve Supabase değerlerini girin.
3. Gradle sync yapın.
4. Uygulamayı çalıştırın.

## Proje Klasör Yapısı
```text
com.benimhesabim.app
├── core
│   ├── common
│   ├── designsystem
│   ├── util
│   └── navigation
├── data
│   ├── local
│   │   ├── database
│   │   ├── dao
│   │   └── entity
│   ├── remote
│   │   ├── supabase
│   │   └── dto
│   ├── repository
│   └── mapper
├── domain
│   ├── model
│   ├── repository
│   └── usecase
├── feature
│   ├── auth
│   ├── home
│   ├── transaction
│   └── settings
└── app
    ├── MainActivity
    └── BenimHesabimApplication
```

## MVP Özellikleri
- Splash ekran
- Email/şifre giriş
- Email/şifre kayıt
- Ana ekranda aylık özet
- Gelir/gider ekleme
- İşlem listesi
- Ayarlar ekranı
- Room tabanlı offline-first kayıt
- Basit Supabase senkronizasyonu

## GitHub'a Yükleme Adımları
Bu projede `gh` kuruluysa ve oturum açıksa repo oluşturulabilir.

1. Git repository başlat:

```bash
git init
```

2. Branch adını `main` yap:

```bash
git branch -M main
```

3. Durumu kontrol et:

```bash
git status
```

4. Şunların commit'e girmediğini doğrula:
- `local.properties`
- `.env`
- `build` klasörleri
- gerçek Supabase key'leri
- keystore dosyaları

5. Dosyaları ekle:

```bash
git add .
```

6. İlk commit:

```bash
git commit -m "Initial commit: Benim Hesabım Android MVP"
```

7. Remote ekle:

```bash
git remote add origin https://github.com/BURAYA_GITHUB_KULLANICI_ADI_GIR/benim-hesabim.git
```

8. Push et:

```bash
git push -u origin main
```

Eğer remote daha önce eklenmişse:

```bash
git remote set-url origin https://github.com/BURAYA_GITHUB_KULLANICI_ADI_GIR/benim-hesabim.git
git push -u origin main
```

`gh` kuruluysa ve repo yoksa:

```bash
gh repo create benim-hesabim --public --source=. --remote=origin --push
```

Push'tan önce tekrar kontrol edin:
- `git status`
- `local.properties` stage edilmiş olmamalı
- gerçek Supabase URL ve anon key commit'e girmemeli
- `README.md`, `.env.example`, `local.properties.example`, `supabase/schema.sql` commit'e dahil olmalı
