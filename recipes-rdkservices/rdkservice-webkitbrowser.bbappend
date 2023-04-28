LIC_FILES_CHKSUM = "file://../LICENSE;md5=16cf2209d4e903e4d5dcd75089d7dfe2"

APPMODULE_BASE_DIRS = "${WPE_APPMODULE}"

inherit onemw_externalsrc
inherit ${@oe.utils.conditional('WPE_AS_APPMODULE', '1', 'onemw_app_module_provider', '', d)}

EXTERNALSRC = ""
# ONEM-22285: do not create any symlinks in source directory -- to allows sstate cache reusability
EXTERNALSRC_SYMLINKS = ""

DEPENDS += "wpeframework-interfaces"

RDEPENDS_${PN} += "wpe-webkit onesdk openssl vmtouch"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}/files:${THISDIR}/${PN}/systemd:${THISDIR}/${PN}/webdriver:"

SRC_URI_remove = "git://github.com/rdkcentral/rdkservices.git;protocol=git;branch=main"
SRC_URI_remove = "git://github.com/rdkcentral/rdkservices.git;protocol=https;branch=main"
SRC_URI_remove = "file://0001-Inject-badger-script-source-for-Pluto.patch;patchdir=../"
SRC_URI_remove = "file://0002-Use-SYSLOG-instead-of-TRACE.patch;patchdir=../"
SRC_URI_remove = "file://0003-Increase-browser-creation-timeout.patch;patchdir=../"
SRC_URI_remove = "file://0004-Reduce-BrowserConsoleLog.patch;patchdir=../"
SRC_URI_remove = "file://0005-Enable-mixed-content.patch;patchdir=../"

SRC_URI += "git://github.com/LibertyGlobal/rdkservices.git;protocol=git;branch=test_wpe_r4_browser"
SRCREV = "886c80732f6bd30bc8001a8837cb2107246effd1"

SRC_URI += "file://wpe-thunder@.service"
SRC_URI += "file://wpe-thunder.conf"
SRC_URI += "file://wpe-thunder.rules"
SRC_URI += "file://wpe_storage_check.sh"
SRC_URI += "file://wpe_remove_old_gst_config.sh"
SRC_URI += "file://wpe_openssl.cnf"
SRC_URI += "${@bb.utils.contains('MACHINE_FEATURES', 'debug', 'file://wpe-thunder-debug.sh', '', d)}"
SRC_URI += "${@bb.utils.contains('MACHINE_FEATURES', 'debug', 'file://webdriver_setup.sh', '', d)}"
SRC_URI += "${@bb.utils.contains('MACHINE_FEATURES', 'debug', 'file://webdriver.service', '', d)}"

WEBKITBROWSER_LOCALSTORAGE ?= "/mnt/wpe_cache"
WEBKITBROWSER_WEBINSPECTOR_ADDRESS ?= "0.0.0.0:8080"
WEBKITBROWSER_MSEBUFFERS = "audio:4m,video:96m,text:1m"
WEBKITBROWSER_CLIENT_CERT_KEY_PASSWD = "XTQEf9G/PBWfzt7Ajx775A3e26nN2f43"
# Below limits define when memory pressure handler will trigger to aggressively free the memory.
# It doesn't mean that process will be terminated over this limit.
WEBKITBROWSER_MEMORYPRESSURE = "storageprocess:50m,networkprocess:50m,webprocess:350m"
WEBKITBROWSER_MEMORYPROFILE = "500m"
WEBKITBROWSER_WINDOWCLOSE = "0"
WEBKITBROWSER_DISKCACHE = "0"
WEBKITBROWSER_DISKCACHEDIR = "/tmp/wpe/.cache"
WEBKITBROWSER_COOKIESTORAGE = "/mnt/wpe_cache/wpe"
WEBKITBROWSER_BROWSERVERSION = "${@'${PREFERRED_VERSION_wpe-webkit}'.replace('%', '')}"

PACKAGECONFIG_remove = "residentapp searchanddiscoveryapp badgerbridge"

EXTRA_OECMAKE += " \
      -DPLUGIN_WEBKITBROWSER_LOCALSTORAGE="${WEBKITBROWSER_LOCALSTORAGE}" \
      -DPLUGIN_WEBKITBROWSER_WEBINSPECTOR_ADDRESS="${WEBKITBROWSER_WEBINSPECTOR_ADDRESS}" \
      -DPLUGIN_WEBKITBROWSER_CLIENT_CERT_KEY_PASSWD="${WEBKITBROWSER_CLIENT_CERT_KEY_PASSWD}" \
      -DPLUGIN_WEBKITBROWSER_WINDOWCLOSE="${WEBKITBROWSER_WINDOWCLOSE}" \
      -DCMAKE_INSTALL_PREFIX=${WPE_APPMODULE}/usr \
      -DPLUGIN_WEBKITBROWSER_COOKIESTORAGE="${WEBKITBROWSER_COOKIESTORAGE}" \
      -DPLUGIN_WEBKITBROWSER_BROWSERVERSION="${WEBKITBROWSER_BROWSERVERSION}" \
"
OECMAKE_TARGET_COMPILE = "WPEFrameworkWebKitBrowser"
#OECMAKE_TARGET_INSTALL = "WebKitBrowser/install"

TARGET_CXXFLAGS += "${@bb.utils.contains('MACHINE_FEATURES', 'debug', '-D_TRACE_LEVEL=2 -D__ENABLE_ASSERT__=ON', '-D_TRACE_LEVEL=1', d)}"

do_install_append() {
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/wpe-thunder@.service ${D}${systemd_unitdir}/system/

    install -d ${D}${sysconfdir}/tmpfiles.d
    install -m 0644 ${WORKDIR}/wpe-thunder.conf ${D}${sysconfdir}/tmpfiles.d/

    install -d ${D}${sysconfdir}/polkit-1/rules.d
    install -m 0644 ${WORKDIR}/wpe-thunder.rules ${D}${sysconfdir}/polkit-1/rules.d

    if ${@oe.utils.conditional('YOCTO_VERSION_MAJOR', '3', 'true', 'false', d)}; then
        install -d ${D}${sysconfdir}/ssl
        install -m 0644 ${WORKDIR}/wpe_openssl.cnf ${D}${sysconfdir}/ssl/
    fi

    install -d ${D}${bindir}
    install -m 0500 ${WORKDIR}/wpe_storage_check.sh ${D}${bindir}/
    install -m 0500 ${WORKDIR}/wpe_remove_old_gst_config.sh ${D}${bindir}/

    if ${@bb.utils.contains('MACHINE_FEATURES', 'debug', 'true', 'false', d)}; then
        install -d ${D}${bindir}/
        install -m 0755 ${WORKDIR}/webdriver_setup.sh ${D}${bindir}/
        install -m 0644 ${WORKDIR}/webdriver.service ${D}${systemd_unitdir}/system/
        install -m 0755 ${WORKDIR}/wpe-thunder-debug.sh ${D}/${bindir}/
    fi

    install -d ${D}${libdir}/wpeframework/plugins
    ln -sfr ${D}${WPE_APPMODULE}${libdir}/wpeframework/plugins/libWPEFrameworkWebKitBrowser.so ${D}${libdir}/wpeframework/plugins/libWPEFrameworkWebKitBrowser.so
    ln -sfr ${D}${WPE_APPMODULE}${libdir}/wpeframework/plugins/libWPEFrameworkWebKitBrowserImpl.so ${D}${libdir}/wpeframework/plugins/libWPEFrameworkWebKitBrowserImpl.so

    install -d ${D}${datadir}/WPEFramework/WebKitBrowser
    ln -sfr ${D}${WPE_APPMODULE}${datadir}/WPEFramework/WebKitBrowser/libWPEInjectedBundle.so ${D}${datadir}/WPEFramework/WebKitBrowser/libWPEInjectedBundle.so

    install -d ${D}${sysconfdir}/WPEFramework/plugins
    ln -sfr ${D}${WPE_APPMODULE}${sysconfdir}/WPEFramework/plugins/WebKitBrowser.json ${D}${sysconfdir}/WPEFramework/plugins/WebKitBrowser.json

    if [ "${WPE_AS_APPMODULE}" != "0" ]; then
        install -m 0440 ${D}${WPE_APPMODULE}${sysconfdir}/WPEFramework/plugins/WebKitBrowser.json ${D}${sysconfdir}/WPEFramework/plugins/WebKitBrowserBase.json
    fi
}

SYSROOT_DIRS += "${WPE_APPMODULE}"

FILES_${PN} += "${systemd_unitdir}/system/wpe-thunder@.service"
FILES_${PN} += "${sysconfdir}/tmpfiles.d/wpe-thunder.conf"
FILES_${PN} += "${sysconfdir}/polkit-1/rules.d/wpe-thunder.rules"
FILES_${PN} += "${@oe.utils.conditional('YOCTO_VERSION_MAJOR', '3', '${sysconfdir}/ssl/wpe_openssl.cnf', '', d)}"
FILES_${PN} += "${@bb.utils.contains('MACHINE_FEATURES', 'debug', '${bindir}/webdriver_setup.sh', '', d)}"
FILES_${PN} += "${@bb.utils.contains('MACHINE_FEATURES', 'debug', '${systemd_unitdir}/system/webdriver.service', '', d)}"
FILES_${PN} += "${@bb.utils.contains('MACHINE_FEATURES', 'debug', '${bindir}/wpe-thunder-debug.sh', '', d)}"
#FILES_${PN} += "${libdir}/wpeframework/plugins/*.so ${datadir}/WPEFramework/WebKitBrowser/*.so ${sysconfdir}"

FILES_${PN} += "${@oe.utils.conditional('WPE_AS_APPMODULE', '1', '', '${WPE_APPMODULE}${libdir}/wpeframework/plugins/*.so', d)}"
FILES_${PN} += "${@oe.utils.conditional('WPE_AS_APPMODULE', '1', '', '${WPE_APPMODULE}${datadir}/WPEFramework/WebKitBrowser/*.so', d)}"
FILES_${PN} += "${@oe.utils.conditional('WPE_AS_APPMODULE', '1', '', '${WPE_APPMODULE}${sysconfdir}', d)}"

# Throw an error when undefined symbol is detected during linking
LDFLAGS_append=" -Wl,--no-undefined"

PRIVATE_LIBS += "libWPEFrameworkWebKitBrowser.so \
                 libWPEFrameworkWebKitBrowserImpl.so \
                 libWPEInjectedBundle.so \
                 "
