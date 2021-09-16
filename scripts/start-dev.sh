#!/bin/bash
#CPU_COUNT=`cat /proc/cpuinfo | grep processor | wc -l`
MEM_OPTS="-server -XX:+UseG1GC -Xms1g -Xmx1g -Dfile.encoding=utf-8 -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M"
jmxJar="/apps/jmx/jmx_prometheus_javaagent-0.12.0.jar"
jmxYaml="/apps/jmx/jmx_exporter.yaml"
JMX_MONITOR=""
if [ -a "$jmxJar" ] && [ -a "$jmxYaml" ]; then
  JMX_MONITOR="-javaagent:/apps/jmx/jmx_prometheus_javaagent-0.12.0.jar=19001:/apps/jmx/jmx_exporter.yaml"
fi

echo "监控jvm: "$JMX_MONITOR
echo "内存参数: "$MEM_OPTS
nohup java $JMX_MONITOR $MEM_OPTS -jar lib/auto-0.0.1-SNAPSHOT.jar  > /dev/null 2>&1 &
pid=$!
echo $pid>.pid
echo "auto 启动成功"
