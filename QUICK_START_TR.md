# ⚡ HIZLI BAŞLANGIÇ

## 1️⃣ Fabric Kur (5 dakika)
- fabricmc.net/use sayfasına git
- Minecraft 1.21.11 seç
- "Download installer" tıkla
- İndirdiğin .jar dosyasını çalıştır
- "Install" tıkla

## 2️⃣ Fabric API İndir
- modrinth.com/mod/fabric-api git
- 1.21.11 versiyonunu indir (0.141.3+1.21.11 veya daha yeni)
- Dosyayı `.minecraft/mods` klasörüne kopyala

## 3️⃣ Bu Modu İndir
- GitHub releases sayfasından .jar indir
- `.minecraft/mods` klasörüne at

## 4️⃣ API Anahtarları Al

### Grok API (AI için):
1. x.ai sayfasına git
2. Hesap oluştur
3. API anahtarını kopyala (`xai-...` ile başlayan)

### OpenAI API (Ses için):
1. platform.openai.com git
2. Hesap oluştur
3. API anahtarını oluştur (`sk-...` ile başlayan)
4. En az $5 bakiye yükle

## 5️⃣ Oyunu Başlat
- Minecraft Launcher'ı aç
- "Fabric" profilini seç
- "Play" tıkla

## 6️⃣ Config Ayarla
Oyunu kapat ve:
1. `.minecraft/config/minecraft-voice-assistant.json` aç
2. API anahtarlarını yapıştır:
```json
{
  "grokApiKey": "xai-BURAYA_YAPISTIR",
  "openaiApiKey": "sk-BURAYA_YAPISTIR"
}
```
3. Kaydet

## 7️⃣ Kullan! 🎉
- Oyunu başlat
- `V` tuşuna bas
- "Merhaba!" de
- `V` tuşunu bırak
- Asistan cevap verecek!

---

## 💡 İPUÇLARI

**İlk kullanımda:**
- Mikrofondan izin iste penceresinde "İzin ver" tıkla
- İlk yanıt 5-10 saniye sürebilir (normal)

**Daha iyi sonuçlar için:**
- Sessiz ortamda konuş
- Net ve yavaş konuş
- Kısa sorular sor

**Sorun yaşıyorsan:**
- `logs/latest.log` dosyasına bak
- GitHub Issues'da sor
- README.md'deki "Sorun Giderme" bölümünü oku

---

## 🎮 ÖRNEK KULLANIM

**Soru 1**: "Demir kılıç nasıl yapılır?"
**Asistan**: "Demir kılıç için 2 demir külçe ve 1 sopa lazım! Dikey olarak yerleştir: üstte 2 demir, altta sopa. Kolayca yaparsın! 🗡️"

**Soru 2**: "Canım az, ne yapmalıyım?"
**Asistan**: "Canın 8 kalp! Hemen yemek ye, et veya golden apple en iyisi. Bir köşeye çekil, moblar yakında. Dikkatli ol! ❤️"

**Soru 3**: "Şaka yap!"
**Asistan**: "Creeper neden psikologa gitmiş? Çünkü patlamaya hazırdı! 💚💥 😄"

İyi eğlenceler! 🎉
