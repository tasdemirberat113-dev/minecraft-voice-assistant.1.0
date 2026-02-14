# 🚀 GELİŞMİŞ ÖZELLİKLER

## 🎯 Direkt Komutlar

Asistan bazı istekleri direkt Minecraft komutlarına çevirebilir!

### Konum Bulma
- **"Köy ara"** → `/locate structure village`
- **"Elmas ara"** → `/locate ore diamond_ore`
- **"Tapınak bul"** → `/locate structure temple`

### Teleport
- **"Teleport 100 64 200"** → `/tp @s 100 64 200`

### Zaman
- **"Gündüz yap"** → `/time set day`
- **"Gece yap"** → `/time set night`

### Hava Durumu
- **"Hava güneşli yap"** → `/weather clear`
- **"Hava yağmurlu yap"** → `/weather rain`
- **"Hava fırtınalı yap"** → `/weather thunder`

### Oyun Modu
- **"Creative mod"** → `/gamemode creative`
- **"Survival mod"** → `/gamemode survival`

---

## 🤖 Grok AI Özellikleri

### Akıllı Bağlam Anlama
Asistan oyundaki durumunu biliyor:
- Sağlık ve açlık seviyeni
- Konumunu (X, Y, Z)
- Bulunduğun biome'u
- Hava durumunu
- Günün saatini
- Elindeki eşyayı
- Yakındaki mobları

### Örnek Akıllı Diyalog:
```
Sen: "Ne yapmalıyım?"
Asistan: "Canın 6 kalp ve yakında zombie var! 
         Hemen yemek ye ve güvenli bir yere çekil. 
         Elinde kılıç var, gerekirse savaş!"
```

### Craft Tarifi Veritabanı
Asistan tüm craftları bilir:
- Temel eşyalar
- Gelişmiş itemler
- Enchantment masası
- Potion tarifleri
- Redstone devreleri

---

## 🎨 Kişiselleştirme

### Config Detayları

```json
{
  // API Anahtarları
  "grokApiKey": "xai-...",
  "openaiApiKey": "sk-...",
  
  // Ses Giriş
  "enableVoiceInput": true,
  "microphoneSensitivity": 0.5,
  
  // Ses Çıkış
  "enableVoiceOutput": true,
  "voiceVolume": 1.0,      // 0.0 - 2.0
  "voiceSpeed": 1.0,       // 0.5 - 2.0
  "voiceModel": "nova",    // Ses karakteri
  
  // AI Ayarları
  "grokModel": "grok-beta",
  "grokTemperature": 0.8,  // 0.0 (robot) - 1.0 (yaratıcı)
  "grokMaxTokens": 500,    // Cevap uzunluğu
  
  // Kişilik
  "personality": "friendly", // friendly, professional, funny
  "language": "tr",          // tr, en
  "useEmojis": true
}
```

### Kişilik Tipleri

**Friendly (Varsayılan):**
- Samimi ve arkadaş canlısı
- Emoji kullanır 😊
- Günlük dil

**Professional:**
- Daha resmi
- Detaylı açıklamalar
- Teknik terimler

**Funny:**
- Sürekli şaka yapar
- Eğlenceli benzetmeler
- Abartılı reaksiyonlar

---

## 🔧 Gelişmiş Kullanım

### Batch İşlemler
```
"Bana demir set yap"
→ Asistan tüm demir zırh parçalarını crafta ekler
```

### Strateji Planlama
```
"Nether'a gitmeye hazırlanmam lazım"
→ Asistan sana checklist verir:
  - Obsidian topla (min 10)
  - Çakmaktaşı yap
  - Demir zırh giy
  - Yiyecek al
  - etc.
```

### Hikaye Modu
```
"Bana macera anlat"
→ Asistan interaktif hikaye anlatır
→ Seçimlere göre hikaye değişir
```

---

## 📊 İstatistikler

### Kullanım Verileri
Mod, isteğe bağlı olarak istatistik tutar:
- Toplam soru sayısı
- En çok sorulan konular
- Ortalama yanıt süresi
- API maliyeti

`config/voice-assistant-stats.json` dosyasında:
```json
{
  "totalQuestions": 156,
  "mostAsked": ["craft", "mob", "location"],
  "avgResponseTime": 2.3,
  "totalCost": 3.45
}
```

---

## 🎮 Multiplayer Desteği

### Paylaşımlı Asistan
Sunucuda herkes aynı asistanı kullanabilir!

Config:
```json
{
  "multiplayerMode": true,
  "sharedContext": true  // Diğer oyuncuların sorularını göster
}
```

### Takım Koordinasyonu
```
Oyuncu 1: "Boss fight stratejisi?"
Asistan: "Tamam! Tank önde, archer arkada, healer ortada!"
Oyuncu 2: "Ben archer'ım, ne yapmalıyım?"
Asistan: "Sen uzaktan ok at, aggro alma!"
```

---

## 🛡️ Güvenlik

### API Anahtar Koruması
- Anahtarlar config dosyasında
- Hiçbir yere gönderilmez
- Log'larda görünmez

### Rate Limiting
Otomatik limit:
- Dakikada max 10 soru
- Saatte max 100 soru
- Koruma: Spam ve maliyet

### Veri Gizliliği
- Konuşmalar kaydedilmez
- Oyun verileri paylaşılmaz
- Sadece API'ye gönderilir

---

## 💡 İpuçları ve Tricks

### En İyi Sonuçlar İçin:
1. **Net konuş** - Gürültüsüz ortam
2. **Kısa sor** - Uzun cümleler yerine kısa sorular
3. **Bağlam ver** - "Demir kılıç için ne lazım?" yerine "Demir kılıç nasıl yapılır?"
4. **Sabırlı ol** - İlk yanıt 3-5 saniye sürebilir

### Easter Eggs:
- "Şaka yap" → Minecraft şakaları
- "Hikaye anlat" → Minecraft hikayeleri
- "Şarkı söyle" → Minecraft parodi şarkıları
- "Herobrine gerçek mi?" → Özel cevap

---

## 🔮 Gelecek Özellikler

Roadmap:
- [ ] Görsel arayüz (GUI)
- [ ] Offline AI modu
- [ ] Daha fazla dil
- [ ] Makro kayıt
- [ ] AI arkadaşlar (NPC)
- [ ] Streaming desteği
- [ ] Discord entegrasyonu

---

İyi eğlenceler! 🎉
