# AircraftWar2026

飞机大战的 Android 版。打开应用后选择难度，拖动战机即可移动，子弹自动发射。首页的“音乐与音效”设置会保存，下次启动时沿用。

## 运行 Android 版

在 Android Studio 中打开当前仓库，选择 `app` 运行配置及 Android 11（API 30）或更新的设备。工程使用 Java、Android SDK 36 和仓库自带的 Gradle Wrapper。

也可在仓库根目录执行 `./gradlew :app:assembleDebug`，APK 位于 `app/build/outputs/apk/debug/app-debug.apk`。

`app/src/main` 是 Android 模块的入口。此前的桌面版代码和素材位于 `src/`。
