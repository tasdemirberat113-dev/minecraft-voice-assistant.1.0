#!/bin/bash

# Gradle wrapper oluşturma scripti

echo "🔧 Gradle wrapper oluşturuluyor..."

# Gradle wrapper indir
gradle wrapper --gradle-version 8.5

echo "✅ Gradle wrapper hazır!"
echo ""
echo "📦 Projeyi derlemek için:"
echo "   ./gradlew build"
echo ""
echo "🎮 Test etmek için:"
echo "   ./gradlew runClient"
