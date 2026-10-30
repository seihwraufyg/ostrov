# Остров — симуляция экосистемы

## Требования
- JDK 10 или новее (используются `var` и `Map.ofEntries`).

## Запуск из терминала
Из корневой папки проекта:

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out island.Main
```

В IntelliJ IDEA откройте папку проекта и запустите `island.Main`.

Основные параметры симуляции находятся в `src/island/config/SimulationConfig.java`.
