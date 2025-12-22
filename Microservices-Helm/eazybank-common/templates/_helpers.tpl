{{/* helpers for common chart */}}
{{ define "common.configmap.data" }}
  SPRING_PROFILES_ACTIVE: {{ .Values.global.activeProfile }}
  SPRING_CONFIG_IMPORT: {{ .Values.global.configServerURL }}
  EUREKA_CLIENT_SERVICEURL_DEFAULTZONE: {{ .Values.global.eurekaServerURL }}
  SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_JWK-SET-URI: {{ .Values.global.keyCloakURL }}
  JAVA_TOOL_OPTIONS: {{ .Values.global.openTelemetryJavaAgent }}
  OTEL_EXPORTER_OTLP_ENDPOINT: {{ .Values.global.otelExporterEndPoint }}
  OTEL_METRICS_EXPORTER: {{ .Values.global.otelMetricsExporter }}
  OTEL_LOGS_EXPORTER: {{ .Values.global.otelLogsExporter }}
  SPRING_CLOUD_STREAM_KAFKA_BINDER_BROKERS: {{ .Values.global.kafkaBrokerURL }}
  # build/version and message broker settings
  BUILD_VERSION: "{{ .Values.global.buildVersion | default "1.0.0" }}"
  SPRING_RABBITMQ_HOST: "{{ .Values.global.rabbitmqHost | default "rabbitmq" }}"
  SPRING_RABBITMQ_PORT: "{{ .Values.global.rabbitmqPort | default "5672" }}"
  SPRING_RABBITMQ_USERNAME: "{{ .Values.global.rabbitmqUsername | default "guest" }}"
  SPRING_RABBITMQ_PASSWORD: "{{ .Values.global.rabbitmqPassword | default "guest" }}"
{{- $srv := dict "ACCOUNTS" (.Values.global.accountsDBHost | default "accountsdb") "LOANS" (.Values.global.loansDBHost | default "loansdb") "CARDS" (.Values.global.cardsDBHost | default "cardsdb") -}}
{{- range $k, $v := $srv }}
  {{ printf "SPRING_DATASOURCE_URL_%s" $k }}: "jdbc:mysql://{{ $v }}:3306/{{ $v }}"
{{- end }}
  SPRING_DATASOURCE_USERNAME: "{{ .Values.global.dbUsername | default "root" }}"
  SPRING_DATASOURCE_PASSWORD: "{{ .Values.global.dbPassword | default "root" }}"
{{ end }}