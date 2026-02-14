#!/bin/bash

echo "========================================"
echo "🔨 Minecraft Voice Assistant - Derleyici"
echo "========================================"
echo ""

# Renkli output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Java kontrolü
if ! command -v java &> /dev/null; then
    echo -e "${RED}[!] Java bulunamadı!${NC}"
    echo ""
    echo "Java 21+ kurmanız gerekiyor:"
    echo "  https://adoptium.net/"
    echo ""
    exit 1
fi

echo -e "${GREEN}[✓] Java bulundu!${NC}"
java -version
echo ""

# Gradle wrapper kontrolü
if [ ! -f "gradlew" ]; then
    echo -e "${YELLOW}[*] Gradle wrapper oluşturuluyor...${NC}"
    
    if command -v gradle &> /dev/null; then
        gradle wrapper --gradle-version 8.5
    else
        echo -e "${RED}[!] Gradle bulunamadı!${NC}"
        echo ""
        echo "GRADLE KURULUMU:"
        echo ""
        echo "Mac (Homebrew):"
        echo "  brew install gradle"
        echo ""
        echo "Linux (apt):"
        echo "  sudo apt install gradle"
        echo ""
        echo "Manuel:"
        echo "  https://gradle.org/install/"
        echo ""
        exit 1
    fi
fi

# Gradlew'i çalıştırılabilir yap
chmod +x gradlew

# Build
echo -e "${YELLOW}[*] Mod derleniyor... (İlk seferde 5-10 dakika sürebilir)${NC}"
echo ""
./gradlew build

# Sonuç kontrolü
if [ $? -eq 0 ]; then
    echo ""
    echo "========================================"
    echo -e "${GREEN}[✓] BAŞARILI!${NC}"
    echo "========================================"
    echo ""
    echo "Mod dosyanız hazır:"
    echo "  build/libs/minecraft-voice-assistant-1.0.0.jar"
    echo ""
    echo "KURULUM:"
    echo "1. Bu .jar dosyasını kopyalayın"
    echo "2. ~/.minecraft/mods/ klasörüne yapıştırın"
    echo "3. Minecraft'i Fabric ile başlatın!"
    echo ""
    echo "API Anahtarlarını eklemeyi unutmayın:"
    echo "  ~/.minecraft/config/minecraft-voice-assistant.json"
    echo ""
else
    echo ""
    echo -e "${RED}[X] HATA: Derleme başarısız!${NC}"
    echo ""
    echo "Detaylı hata için:"
    echo "  ./gradlew build --stacktrace"
    echo ""
    exit 1
fi
