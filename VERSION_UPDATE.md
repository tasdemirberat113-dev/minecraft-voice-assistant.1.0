# 🔄 VERSİYON GÜNCELLEMESİ - 1.21.11

## ✅ Güncellenen Versiyonlar

Projeniz başarıyla **Minecraft 1.21.11** için güncellendi!

### Önceki Versiyonlar:
- ❌ Minecraft: 1.21.1
- ❌ Fabric Loader: 0.15.11
- ❌ Fabric API: 0.100.4+1.21.1
- ❌ Yarn Mappings: 1.21.1+build.3
- ❌ Loom: 1.6

### Yeni Versiyonlar:
- ✅ Minecraft: **1.21.11** (Mounts of Mayhem - Aralık 2025)
- ✅ Fabric Loader: **0.18.4** (24 Aralık 2025)
- ✅ Fabric API: **0.141.3+1.21.11** (3 Şubat 2026)
- ✅ Yarn Mappings: **1.21.11+build.4** (En güncel)
- ✅ Loom: **1.14-SNAPSHOT** (1.21.11 için önerilen)

## 📝 Değiştirilen Dosyalar

1. **gradle.properties**
   - Minecraft versiyonu: 1.21.1 → 1.21.11
   - Fabric Loader: 0.15.11 → 0.18.4
   - Fabric API: 0.100.4+1.21.1 → 0.141.3+1.21.11
   - Yarn Mappings: 1.21.1+build.3 → 1.21.11+build.4

2. **build.gradle**
   - Loom: 1.6-SNAPSHOT → 1.14-SNAPSHOT

3. **fabric.mod.json**
   - Minecraft dependency: ~1.21.1 → ~1.21.11
   - Fabric Loader: >=0.15.11 → >=0.18.4

4. **README.md**
   - Tüm versiyon referansları güncellendi
   - Kurulum talimatları güncellendi

5. **QUICK_START_TR.md**
   - Hızlı başlangıç adımları güncellendi
   - Doğru versiyon numaraları eklendi

## 🎮 Minecraft 1.21.11 - Mounts of Mayhem Hakkında

### Yeni Özellikler:
- 🐫 **Cadaver Camel** - Zombi deve
- 🐚 **Nautilus** - Binebilir deniz canavarı
- ⚔️ **Spear** (Mızrak) - Yeni silah tipi
- 🛡️ **Nautilus Armor** - Nautilus için zırh
- 🏃 **Dash Mekanikleri** - Hızlı koşma sistemi

### Önemli Değişiklikler:
- Son obfuscated (karıştırılmış) versiyon
- Bir sonraki versiyon (26.1) artık obfuscated olmayacak
- Yarn mappings bu versiyondan sonra güncellenmiyor
- Gelecek versiyonlar için Mojang Mappings kullanılacak

## 🚀 Kullanım Talimatları

### Derleme:
```bash
cd minecraft-voice-assistant
./gradlew build
```

### Test:
```bash
./gradlew runClient
```

### Kurulum:
1. `build/libs/minecraft-voice-assistant-1.0.0.jar` dosyasını al
2. `.minecraft/mods/` klasörüne kopyala
3. Minecraft 1.21.11 ile Fabric Loader 0.18.4 kullan
4. Fabric API 0.141.3+1.21.11 veya daha yeni bir versiyon yükle

## ⚠️ Önemli Notlar

1. **Java 21+ Gerekli**: Minecraft 1.21.11 için Java 21 veya daha yeni versiyon gerekir

2. **Fabric API Zorunlu**: Mod çalışması için Fabric API 0.141.3+1.21.11 veya daha yeni versiyon mutlaka yüklenmeli

3. **API Anahtarları**: Unutma!
   - Grok API: https://x.ai/
   - OpenAI API: https://platform.openai.com/

4. **Config Dosyası**: İlk çalıştırmadan sonra `.minecraft/config/minecraft-voice-assistant.json` dosyasına API anahtarlarını ekle

## 🐛 Sorun Giderme

### "Minecraft versiyon uyumsuzluğu" hatası:
- Minecraft 1.21.11 kullandığından emin ol
- Launcher'da Fabric profili seçili olmalı

### "Fabric Loader bulunamadı" hatası:
- Fabric Loader 0.18.4 yüklü olmalı
- fabricmc.net/use adresinden yükle

### "Mod yüklenemedi" hatası:
- Fabric API 0.141.3+1.21.11 yüklü mü kontrol et
- logs/latest.log dosyasını incele

## 📚 Ek Kaynaklar

- **Fabric Dokümantasyonu**: https://docs.fabricmc.net/
- **1.21.11 Değişiklikleri**: https://fabricmc.net/2025/12/05/12111.html
- **Minecraft 1.21.11 Notları**: https://www.minecraft.net/en-us/article/minecraft-java-edition-1-21-11

## 🎉 Hazır!

Projeniz şimdi **Minecraft 1.21.11 (Mounts of Mayhem)** için tam uyumlu!

İyi kodlamalar! 🚀
