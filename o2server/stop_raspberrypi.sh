sudo $(cd "$(dirname "$0")"; pwd)/jvm/raspberrypi/bin/java -javaagent:$(cd "$(dirname "$0")"; pwd)/console.jar -cp $(cd "$(dirname "$0")"; pwd)/console.jar com.x.server.console.swapcommand.Exit