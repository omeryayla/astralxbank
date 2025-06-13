# astralxbank
A modern banking application project built with Java Spring Boot and microservices architecture.

## Proje Kurulumu

1. Depoyu klonlayın ve proje klasörüne geçin.
2. JDK 21 ve PostgreSQL kurulu olmalıdır.
3. `src/main/resources/application.yml` içerisindeki veritabanı ayarlarını kontrol edin veya ihtiyaca göre güncelleyin.

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/bankdb
    username: bankuser
    password: bankpass
```

4. PostgreSQL üzerinde aşağıdaki komutlarla veritabanı ve kullanıcı oluşturabilirsiniz:

```sql
CREATE DATABASE bankdb;
CREATE USER bankuser WITH PASSWORD 'bankpass';
GRANT ALL PRIVILEGES ON DATABASE bankdb TO bankuser;
```

## Uygulamayı Çalıştırma

Proje Maven Wrapper kullanır, bu nedenle ek Maven kurulumu gerekmez.

```bash
# derleme
./mvnw clean package

# uygulamayı başlatma
./mvnw spring-boot:run
```

Uygulama başlatıldığında `http://localhost:8080` adresinde çalışır.

### Örnek Komutlar

```bash
# kullanıcı kaydı
curl -X POST http://localhost:8080/auth/register \
  -H 'Content-Type: application/json' \
  -d '{"username":"demo","email":"demo@example.com","password":"pass"}'

# giriş yap ve JWT token al
TOKEN=$(curl -s -X POST http://localhost:8080/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"username":"demo","password":"pass"}')

# hesap oluştur
curl -X POST http://localhost:8080/account/create \
  -H "Authorization: Bearer $TOKEN"

# hesapları listele
curl http://localhost:8080/account/my-accounts \
  -H "Authorization: Bearer $TOKEN"
```

## Swagger Arayüzü

API dokümantasyonuna [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) adresinden erişebilirsiniz. JWT tokenınızı **Authorize** bölümünden girerek korumalı uç noktalara istek gönderebilirsiniz.
