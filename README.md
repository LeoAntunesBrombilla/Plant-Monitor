# 🌱 Plant Monitor - Sistema IoT Completo

Sistema de monitoramento de umidade do solo com arquitetura IoT em 3 camadas (Edge, Fog, Cloud).

---

## 📋 Pré-requisitos

### Hardware:
- ESP32 DevKit v1
- Sensor de umidade do solo (com módulo)
- 3 jumpers macho-fêmea
- Cabo Micro USB

### Software:
- Arduino IDE
- Android Studio
- Conta ThingSpeak (gratuita)

---

## 🔧 Configuração do Ambiente

### 1. ThingSpeak - Criar Canal

1. Acesse: https://thingspeak.com/
2. **Sign Up** (criar conta gratuita)
3. **Channels → My Channels → New Channel**
4. Configure:
    - **Name:** Plant Monitor
    - **Field 1:** Moisture Percent
    - **Field 2:** Moisture Raw
5. **Save Channel**
6. Vá em **API Keys** e copie:
    - **Write API Key** (ex: `ABCD1234EFGH5678`)
    - **Channel ID** (ex: `123456`)

---

## 🔌 Montagem do Hardware
```
Sensor → ESP32 DevKit v1
-------------------------
VCC → 3V3
GND → GND
A0  → D34 (GPIO 34)
```

⚠️ **IMPORTANTE:** Use 3.3V, NÃO use 5V!

---

## 💻 Configuração do Código ESP32

### Variáveis a modificar no código:
```cpp
// WiFi da sua rede
const char* ssid = "SEU_WIFI";           // ← Nome do WiFi
const char* password = "SUA_SENHA";      // ← Senha do WiFi

// ThingSpeak
String apiKey = "SEU_WRITE_API_KEY";     // ← Write API Key do ThingSpeak
```

### Upload:
1. Conecte ESP32 via USB
2. Selecione a porta: **Ferramentas → Porta**
3. Clique em **Upload** (seta →)
4. Se necessário, segure botão **BOOT** até começar upload

---

## 📱 Configuração do App Android

### Variáveis a modificar no código:
```kotlin
// Em MainActivity.kt
val url = "https://api.thingspeak.com/channels/SEU_CHANNEL_ID/feeds/last.json"
```

### AndroidManifest.xml - Verificar permissões:
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

### network_security_config.xml:

Arquivo já incluído no projeto. Permite requisições HTTP.

### Código do arduino está em app/src/main/arduino.txt
---

## 🚀 Como Rodar

### 1. ESP32:
```
1. Upload do código
2. Abrir Serial Monitor (115200 baud)
3. Verificar conexão WiFi
4. Confirmar envio de dados: "Data sent to cloud! ✅"
5. Código do arduino está em app/src/main/arduino.txt
```

### 2. ThingSpeak:
```
1. Acessar: thingspeak.com
2. Channels → My Channels → Plant Monitor
3. Ver gráficos com dados em tempo real
```

### 3. App Android:
```
1. Build e instalar no celular
2. Abrir app
3. Dados aparecem automaticamente da nuvem
```

---

## 📊 Dados Enviados

**JSON Format:**
```json
{
  "field1": 45,  
  "field2": 2234  
}
```

**Intervalo:** A cada 20 segundos (limite free do ThingSpeak: 15s)

---

## 🔍 Verificação de Funcionamento

### ESP32 (Serial Monitor):
```
Moisture: 45% (Raw: 2234)
ThingSpeak response: 200
Data sent to cloud! ✅
```

### ThingSpeak:
- Gráficos atualizando em tempo real
- Field 1: Porcentagem
- Field 2: Valor bruto

### App Android:
- Status: "Connected to cloud ☁️"
- Mostra: Porcentagem e valor raw
- Atualiza a cada 5 segundos

---

## 📝 Especificações Técnicas

**ESP32:**
- Microcontrolador: 240 MHz dual-core
- ADC: 12-bit (0-4095)
- WiFi: 802.11 b/g/n 2.4GHz
- Tensão: 3.3V

**Sensor:**
- Tipo: Capacitivo resistivo
- Saída: Analógica (A0) e Digital (D0)
- Alimentação: 3.3V

**ThingSpeak:**
- Plano: Free
- Limite: 3 milhões mensagens/ano
- Update rate: 15 segundos mínimo
- API: REST JSON

---

## 📹 Demonstração

Demonstra:
1. ESP32 coletando dados do sensor
2. Dados chegando no ThingSpeak Cloud
3. App Android acessando dados de qualquer lugar
4. Arquitetura IoT completa funcionando

---

## 👨‍💻 Autor

[Leonardo Antunes]  
Trabalho Individual - Sistemas IoT  
Data: 22/12/2025

---

## 📄 Licença

Este projeto é para fins educacionais.