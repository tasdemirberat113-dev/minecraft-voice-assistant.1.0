# 🔨 MOD'U JAR DOSYASINA ÇEVIRME REHBERİ

## 🎯 Hedef: Çalışır Fabric Mod JAR Dosyası

Bu rehber, kaynak kodlardan **minecraft-voice-assistant-1.0.0.jar** dosyasını oluşturmanı sağlar.

---

## 📋 GEREKSINIMLER

Derleme için bunlar gerekli:

✅ **Java Development Kit (JDK) 21+**
- İndir: https://adoptium.net/
- Kontrol: `java -version`

✅ **Gradle** (Opsiyonel, wrapper kullanılacak)
- Otomatik indirilecek, manuel kurulum gerekmez

✅ **İnternet bağlantısı**
- Bağımlılıkları (Minecraft, Fabric) indirmek için

---

## 🪟 WINDOWS İÇİN DERLEME

### Yöntem 1: Otomatik (ÖNERİLEN)

1. Proje klasörünü aç
2. `compile.bat` dosyasını **çift tıkla**
3. Bekle (ilk seferde 5-10 dakika sürebilir)
4. Hazır! JAR dosyası: `build\libs\minecraft-voice-assistant-1.0.0.jar`

### Yöntem 2: Manuel

```batch
# Terminal aç (CMD veya PowerShell)
cd minecraft-voice-assistant

# Gradle wrapper oluştur (ilk seferde)
gradle wrapper --gradle-version 8.5

# Derle
gradlew.bat build

# Sonuç
dir build\libs\*.jar
```

### Gradle yoksa:

**Chocolatey ile:**
```batch
choco install gradle
```

**Manuel:**
1. https://gradle.org/install/ git
2. Gradle 8.5 indir
3. PATH'e ekle

---

## 🐧 LINUX / MAC İÇİN DERLEME

### Yöntem 1: Otomatik (ÖNERİLEN)

```bash
cd minecraft-voice-assistant
chmod +x compile.sh
./compile.sh
```

### Yöntem 2: Manuel

```bash
cd minecraft-voice-assistant

# Gradle wrapper oluştur (ilk seferde)
gradle wrapper --gradle-version 8.5

# Derle
./gradlew build

# Sonuç
ls -lh build/libs/*.jar
```

### Gradle Kurulum:

**Mac (Homebrew):**
```bash
brew install gradle
```

**Linux (apt):**
```bash
sudo apt install gradle
```

**Linux (manual):**
```bash
wget https://services.gradle.org/distributions/gradle-8.5-bin.zip
unzip gradle-8.5-bin.zip
sudo mv gradle-8.5 /opt/
export PATH=$PATH:/opt/gradle-8.5/bin
```

---

## 🌐 ONLINE DERLEME (Gradle Yok / Hızlı Test)

Eğer Gradle kurmak istemiyorsan:

### GitHub ile:

1. GitHub hesabı oluştur
2. Bu projeyi upload et
3. GitHub Actions kullan (otomatik build)
4. JAR dosyasını artifacts'ten indir

### Replit ile:

1. replit.com hesabı aç
2. "Import from GitHub" → Projeyi yükle
3. `./gradlew build` komutu çalıştır
4. JAR'ı indir

---

## 📦 DERLEME SONRASI

Derleme başarılı olursa:

```
build/
  └── libs/
      └── minecraft-voice-assistant-1.0.0.jar  ← MOD DOSYAN!
```

### Kurulum:

1. **JAR dosyasını kopyala**
   - Windows: `build\libs\minecraft-voice-assistant-1.0.0.jar`
   - Linux/Mac: `build/libs/minecraft-voice-assistant-1.0.0.jar`

2. **Mods klasörüne yapıştır**
   - Windows: `%APPDATA%\.minecraft\mods\`
   - Mac: `~/Library/Application Support/minecraft/mods/`
   - Linux: `~/.minecraft/mods/`

3. **Gereksinimler kontrol**
   - ✅ Minecraft 1.21.11
   - ✅ Fabric Loader 0.18.4
   - ✅ Fabric API 0.141.3+1.21.11

4. **API Anahtarları**
   - Config dosyası: `.minecraft/config/minecraft-voice-assistant.json`
   - Grok API: https://x.ai/
   - OpenAI API: https://platform.openai.com/

---

## ❌ HATA GİDERME

### "Java bulunamadı"
```bash
# Java yüklü mü?
java -version

# Değilse:
# https://adoptium.net/ adresinden JDK 21 indir
```

### "Gradle bulunamadı"
```bash
# Gradlew kullan (otomatik indirir)
./gradlew build  # Linux/Mac
gradlew.bat build  # Windows
```

### "Could not resolve dependencies"
```bash
# İnternet bağlantısını kontrol et
# Gradle cache temizle
./gradlew clean build --refresh-dependencies
```

### "Task failed with an exception"
```bash
# Detaylı log
./gradlew build --stacktrace

# Veya
./gradlew build --info
```

### "Unsupported class file version"
```bash
# Java versiyonu eski
# JDK 21+ kur
java -version
```

---

## 🚀 HIZLI ÖZET

**En basit yol:**

```bash
# 1. Proje klasörüne git
cd minecraft-voice-assistant

# 2. Derle
./gradlew build          # Linux/Mac
gradlew.bat build        # Windows

# 3. Kopyala
cp build/libs/*.jar ~/.minecraft/mods/
```

**Hazır!** 🎉

---

## 💡 İPUÇLARI

### Hızlı derleme:
```bash
./gradlew build --parallel
```

### Cache temizle:
```bash
./gradlew clean
```

### Sadece JAR oluştur (test etme):
```bash
./gradlew jar
```

### Minecraft'ta test et:
```bash
./gradlew runClient
```

---

## 📊 DERLEME SÜRELERİ

- **İlk derleme**: 5-10 dakika (bağımlılıklar indirilir)
- **İkinci derleme**: 30-60 saniye (cache kullanır)
- **Temiz derleme**: 1-2 dakika

---

## 🆘 YARDIM

Sorun mu yaşıyorsun?

1. **Log dosyasını kontrol et**: `build/libs/`
2. **GitHub Issues**: Proje sayfasında issue aç
3. **Fabric Dokümantasyon**: https://fabricmc.net/develop/

---

İyi kodlamalar! 🔨🎮
