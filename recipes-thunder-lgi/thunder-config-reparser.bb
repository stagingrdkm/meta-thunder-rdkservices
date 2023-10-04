# Copyright (c) 2023 Liberty Global. All rights reserved.
# ============================================================================
#
SUMMARY = "LGI Application to reparse thunder configs"
LICENSE = "CLOSED"
DEPENDS = "jsoncpp rdk-logger"
RDEPENDS_{PN} = "jsoncpp rdk-logger"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://messagingTemplate.json"

ONEMW_SRC_SUBPATH = "rdk/${BPN}"

inherit onemwsrc cmake

FILES_${PN} += "${bindir}/*"

do_install_append() {
    install -d ${D}${sysconfdir}/WPEFramework
    install -m 0644 ${WORKDIR}/messagingTemplate.json ${D}${sysconfdir}/WPEFramework/messagingTemplate.json
}

FILES_${PN} += "${sysconfdir}/WPEFramework"

