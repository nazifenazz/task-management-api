# Task Management API

Bu proje benim Spring Boot pratik yapmak için geliştirdiğim görev yönetim sistemi projesidir.

Projede kullanıcı kayıt olabilir, giriş yapabilir, proje oluşturabilir ve seçilen projeye görev ekleyebilir.  
Aynı zamanda kullanıcı sadece kendi görevlerini görebilir ve görevleri durumuna göre filtreleyebilir.

Bu projeyi yaparken amacım sadece CRUD yapmak değil, biraz daha gerçek bir proje mantığını görmekti.

## Bu projede neler var

- kullanıcı kayıt olma
- kullanıcı giriş yapma
- proje oluşturma
- proje listeleme
- görev oluşturma
- görev listeleme
- görev filtreleme
- validation kullanımı
- global exception handling
- spring security
- aop ile loglama
- auditing

## Kullandığım teknolojiler

- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- PostgreSQL
- Maven
- Lombok

## Entity yapısı

### Kullanici

Kullanıcı bilgilerini tutar.

- id
- ad
- email
- sifre

### Proje

Projeye ait temel bilgileri tutar.

- id
- ad
- aciklama
- createdAt
- updatedAt

### Gorev

Göreve ait bilgileri tutar.

- id
- baslik
- aciklama
- durum
- oncelik
- sonTarih
- createdAt
- updatedAt

## Enum yapıları

### GorevDurumu

- TODO
- IN_PROGRESS
- DONE

### GorevOnceligi

- LOW
- MEDIUM
- HIGH

## Endpointler

### Auth işlemleri

- `POST /auth/register`
- `POST /auth/login`

### Proje işlemleri

- `POST /projeler`
- `GET /projeler`
- `GET /projeler/{id}`
- `PUT /projeler/{id}`
- `DELETE /projeler/{id}`

### Görev işlemleri

- `POST /tasks`
- `GET /tasks`
- `GET /tasks/{id}`
- `PUT /tasks/{id}`
- `DELETE /tasks/{id}`

## Görev filtreleme

Görevleri şu alanlara göre filtreleyebiliyorum:

- durum
- oncelik
- projeId
- dueBefore

Örnek kullanım:

```http
GET /tasks?durum=TODO
```

## Güvenlik kısmı

Bu projede Spring Security kullandım.

- `/auth/**` açık
- diğer endpointler giriş istiyor
- giriş yapan kullanıcı bilgisi security context içinden alınıyor

## Auditing kısmı

Ortak alanları tekrar tekrar yazmamak için `BaseEntity` kullandım.

Burada:

- createdAt
- updatedAt

alanları otomatik doluyor.

## AOP kısmı

Bu projede loglama için aspect kullandım.

Şunları logluyor:

- controller metodları başladı mı
- service metodları başladı mı
- metod bitti mi
- ne kadar sürdü
- hata oldu mu

## Projeyi çalıştırmak için

### 1. PostgreSQL içinde veritabanı oluştur

Örnek veritabanı adı:

```text
gorevyonetim
```

### 2. `application.properties` dosyasını düzenle

Kendi bilgisayarındaki veritabanı bilgilerine göre şunları ayarla:

- veritabanı adı
- kullanıcı adı
- şifre
- port

### 3. Uygulamayı çalıştır

Terminalden:

```bash
./mvnw spring-boot:run
```

veya IntelliJ içinden çalıştırabilirsin.

### 4. Tarayıcıdan aç

```text
http://localhost:8080
```
