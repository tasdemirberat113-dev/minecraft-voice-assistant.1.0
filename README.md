# 🎮 Minecraft Sesli Asistan (Grok AI Destekli)

Minecraft 1.21.11 için **süper güçlü** Fabric mod! Grok AI ile sesli konuşarak yardım al! 🚀

## ✨ Özellikler

- 🎤 **Sesli Komutlar**: Mikrofonla konuş
- 🗣️ **Sesli Yanıtlar**: Asistan sana sesle cevap verir
- 🧠 **Grok AI**: En güçlü yapay zeka
- 📊 **Oyun Bilgisi**: Envanterini, konumunu, yakındaki mobları bilir
- 🎯 **Akıllı Yardım**: Craft tarifleri, stratejiler, ipuçları
- 😄 **Eğlenceli Kişilik**: Samimi ve enerjik asistan

## 📋 Gereksinimler

- **Minecraft**: 1.21.11 (Java Edition)
- **Fabric Loader**: 0.18.4+
- **Fabric API**: 0.141.3+
- **Java**: 21+
- **API Anahtarları**:
  - Grok API (https://x.ai/ - AI için)
  - OpenAI API (https://openai.com/ - Ses için)

## 🔧 Kurulum

### 1. Fabric Loader'ı Kur
1. https://fabricmc.net/use/ adresine git
2. Minecraft 1.21.11 için Fabric Loader'ı indir ve yükle

### 2. Fabric API'yi Kur
1. https://modrinth.com/mod/fabric-api adresine git
2. 1.21.11 versiyonunu indir (0.141.3+1.21.11 veya daha yeni)
3. `.minecraft/mods` klasörüne at

### 3. Bu Modu Kur
1. Projeyi derle: `./gradlew build`
2. `build/libs/minecraft-voice-assistant-1.0.0.jar` dosyasını al
3. `.minecraft/mods` klasörüne at

### 4. API Anahtarlarını Ayarla
Oyunu bir kez başlat, sonra:
1. `.minecraft/config/minecraft-voice-assistant.json` dosyasını aç
2. API anahtarlarını ekle:
```json
{
  "grokApiKey": "xai-SENIN_ANAHTARIN",
  "openaiApiKey": "sk-SENIN_ANAHTARIN",
  ...
}
```

## 🎮 Kullanım

### Asistanı Aktifleştir
**V tuşuna bas ve konuş!**

1. `V` tuşuna bas (kayıt başlar)
2. Sorununu sor: *"Demir zırh nasıl yapılır?"*
3. `V` tuşunu bırak
4. Asistan sesle cevap verir! 🎉

### Örnek Sorular

**Craft Tarifleri:**
- "Kılıç nasıl yapılır?"
- "Elmas zırh için ne lazım?"
- "Enchantment table crafti nedir?"

**Strateji:**
- "Ender dragon'u nasıl yenerim?"
- "En iyi mining stratejisi nedir?"
- "Villager trade nasıl yapılır?"

**Durum Sorguları:**
- "Envanterimde ne var?"
- "Yakınımda düşman var mı?"
- "Hangi biome'dayım?"

**Genel:**
- "En iyi enchantmentlar nedir?"
- "Nether'a nasıl giderim?"
- "Şaka yap!"

## 🔊 Ses Ayarları

Config dosyasından ayarlayabilirsin:
```json
{
  "enableVoiceInput": true,      // Mikrofon açık/kapalı
  "enableVoiceOutput": true,     // Sesli yanıt açık/kapalı
  "voiceVolume": 1.0,            // Ses seviyesi (0.0 - 1.0)
  "voiceSpeed": 1.0,             // Konuşma hızı (0.5 - 2.0)
  "voiceModel": "nova"           // Ses modeli
}
```

### Kullanılabilir Ses Modelleri:
- `alloy` - Nötr, dengeli
- `echo` - Erkek, profesyonel
- `fable` - İngiliz aksanlı
- `onyx` - Derin, otoriter
- `shimmer` - Yumuşak, samimi
- `nova` - Enerjik, genç (varsayılan)

## 🛠️ Geliştirme

### Projeyi Derle
```bash
./gradlew build
```

### Test Et
```bash
./gradlew runClient
```

### Kod Yapısı
```
src/main/java/com/minecraftai/assistant/
├── MinecraftVoiceAssistant.java  # Ana mod
├── GrokAPIClient.java             # Grok AI entegrasyonu
├── VoiceRecorder.java             # Ses kayıt (STT)
├── TextToSpeech.java              # Sesli yanıt (TTS)
├── MinecraftContextProvider.java  # Oyun bilgisi
└── Config.java                    # Ayarlar
```

## 🐛 Sorun Giderme

### "API anahtarı geçersiz" hatası
- Config dosyasındaki API anahtarlarını kontrol et
- Grok: https://console.x.ai/
- OpenAI: https://platform.openai.com/api-keys

### Mikrofon çalışmıyor
- İşletim sistemi izinlerini kontrol et
- Java'nın mikrofon erişimi var mı?
- `logs/latest.log` dosyasına bak

### Ses duyulmuyor
- Ses seviyesini kontrol et
- `voiceVolume` değerini artır
- Hoparlör/kulaklık bağlantısını kontrol et

### Mod yüklenmiyor
- Fabric Loader versiyonunu kontrol et (0.15.11+)
- Fabric API yüklü mü?
- Java 21+ kullanıyor musun?

## 📊 Performans

- **RAM Kullanımı**: ~200MB (ek)
- **CPU**: Ses işleme sırasında orta
- **Network**: API çağrıları sırasında aktif

## 🔐 Güvenlik ve Gizlilik

- API anahtarları **yerel config dosyasında** saklanır
- Sesler sadece API'ye gönderilir, saklanmaz
- Oyun verileri paylaşılmaz
- Açık kaynak - kodu inceleyebilirsin!

## 🤝 Katkıda Bulunma

1. Fork yap
2. Feature branch oluştur: `git checkout -b yeni-ozellik`
3. Commit at: `git commit -m 'Harika özellik ekledim'`
4. Push yap: `git push origin yeni-ozellik`
5. Pull Request aç!

## 📝 TODO

- [ ] Daha fazla dil desteği
- [ ] Offline mod (yerel AI)
- [ ] Görsel arayüz (GUI)
- [ ] Komut geçmişi
- [ ] Favoriler sistemi
- [ ] Multiplayer desteği
- [ ] Voice şakalar ve easter eggs
- [ ] Mod uyumluluk (JEI, REI vb)

## 📜 Lisans

MIT License - Özgürce kullan, değiştir, paylaş!

## 💰 API Maliyetleri

**Grok API**: https://x.ai/pricing
**OpenAI STT**: $0.006/dakika
**OpenAI TTS**: $0.015/1K karakter

Örnek: 100 soru-cevap = ~$2-5

## 🎉 İyi Oyunlar!

Sorular? Sorunlar? GitHub Issues'da paylaş!

**Not**: Bu mod resmi Mojang/Microsoft ürünü değildir.
