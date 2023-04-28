#!/bin/sh

. /usr/bin/lxc_log_level.inc

WEBKIT_CONFIG=/tmp/WebKitBrowser.json.$$
WEBINSPECTOR_PORT=9226
WEBDRIVER_PORT=9517
WEBDRIVER_HOST=0.0.0.0

CURL=/usr/bin/curl
RM=/bin/rm
CAT=/bin/cat
SED=/bin/sed
ECHO=/bin/echo
PIDOF=/bin/pidof
LXC=/usr/bin/lxc-attach
DBUS=/usr/bin/dbus-send
IPTABLES=/usr/sbin/iptables

${ECHO} "IPTables flush all chains in the table"
${IPTABLES}-save > ${WEBKIT_CONFIG}.iptables
${IPTABLES} -F
${ECHO} "Deactivate WebKitBrowser & OCDM"
${DBUS} --system --dest=com.lgi.rdk.utils.awc.server /com/lgi/rdk/utils/awc/awc com.lgi.rdk.utils.awc.awc.Stop int32:`${PIDOF} WPEWebProcess` int32:0
${CURL} -s -X PUT http://127.0.0.1:9998/Service/Controller/Deactivate/OCDM
${ECHO} "Download WebKitBrowser.json configuration"
${CURL} -s http://127.0.0.1:9998/Service/Controller/Configuration/WebKitBrowser > ${WEBKIT_CONFIG}
${CAT} ${WEBKIT_CONFIG} | ${SED} 's/\"inspector\":\"0.0.0.0:8080\",/\"inspector\":\"'${WEBDRIVER_HOST}':'${WEBINSPECTOR_PORT}'\",\n  \"automation\": true,/g' > ${WEBKIT_CONFIG}.automation
${ECHO} "Upload WebKitBrowser.json, enabling WebDriver configuration"
${CURL} http://127.0.0.1:9998/Service/Controller/Configuration/WebKitBrowser --upload-file ${WEBKIT_CONFIG}.automation
${ECHO} "Activating WebKitBrowser & OCDM"
${CURL} -s -X PUT http://127.0.0.1:9998/Service/Controller/Activate/OCDM
${DBUS} --system --dest=com.lgi.rdk.utils.awc.server /com/lgi/rdk/utils/awc/awc com.lgi.rdk.utils.awc.awc.Start string:'thunderwpe' array:string:""
${ECHO} "Launch WPEWebDriver, port=${WEBDRIVER_PORT} host=${WEBDRIVER_HOST} (ctrl+c to exit)"

trap restore EXIT

function restore() {
    ${ECHO} "Upload WebKitBrowser.json, restoring configuration"
    ${CURL} http://127.0.0.1:9998/Service/Controller/Configuration/WebKitBrowser --upload-file ${WEBKIT_CONFIG}
    ${ECHO} "IPTables restore config"
    ${IPTABLES}-restore < ${WEBKIT_CONFIG}.iptables
    ${ECHO} "Remove temp files"
    ${RM} ${WEBKIT_CONFIG} ${WEBKIT_CONFIG}.automation ${WEBKIT_CONFIG}.iptables
    ${ECHO} "Bye!"
}

${LXC} -n WPEThunder -o none  -l ${LXC_LOG_LEVEL} -f /container/WPEThunder/conf/lxc.conf -s "PID|UTSNAME|IPC|NETWORK" -R -- /appmodule/wpe/usr/bin/WPEWebDriver --port=${WEBDRIVER_PORT} --host=${WEBDRIVER_HOST}
