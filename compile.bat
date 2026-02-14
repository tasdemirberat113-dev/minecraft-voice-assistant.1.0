@echo off
echo ========================================
echo Minecraft Voice Assistant - Derleyici
echo ========================================
echo.

REM Gradle kontrolü
where gradle >nul 2>nul
if %errorlevel% neq 0 (
    echo [!] Gradle bulunamadi!
    echo.
    echo GRADLE KURULUMU:
    echo 1. https://gradle.org/install/ adresine git
    echo 2. Gradle'i indir ve yukle
    echo 3. Bu scripti tekrar calistir
    echo.
    echo ALTERNATIF: Chocolatey ile kur
    echo   choco install gradle
    echo.
    pause
    exit /b 1
)

echo [+] Gradle bulundu!
echo.

REM Java kontrolü
where java >nul 2>nul
if %errorlevel% neq 0 (
    echo [!] Java bulunamadi!
    echo.
    echo Java 21+ kurmaniz gerekiyor:
    echo https://adoptium.net/
    echo.
    pause
    exit /b 1
)

echo [+] Java bulundu!
java -version
echo.

echo [*] Derleme basliyor...
echo.

REM Gradle wrapper oluştur
if not exist "gradlew.bat" (
    echo [*] Gradle wrapper olusturuluyor...
    gradle wrapper --gradle-version 8.5
)

REM Build
echo [*] Mod derleniyor... (Bu biraz zaman alabilir)
echo.
call gradlew.bat build

if %errorlevel% equ 0 (
    echo.
    echo ========================================
    echo [v] BASARILI!
    echo ========================================
    echo.
    echo Mod dosyaniz hazirlandi:
    echo   build\libs\minecraft-voice-assistant-1.0.0.jar
    echo.
    echo KURULUM:
    echo 1. Bu .jar dosyasini kopyala
    echo 2. .minecraft\mods\ klasorune yapistir
    echo 3. Minecraft'i Fabric ile baslat!
    echo.
    echo API Anahtarlarini eklemeyi unutma:
    echo   .minecraft\config\minecraft-voice-assistant.json
    echo.
) else (
    echo.
    echo [X] HATA: Derleme basarisiz!
    echo.
    echo Log dosyasini kontrol et:
    echo   build\libs\
    echo.
)

pause
