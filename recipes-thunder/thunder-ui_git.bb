SUMMARY = "WPE Framework User Interface"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=66fe57b27abb01505f399ce4405cfea0"

PROVIDES += "wpeframework-ui"
RPROVIDES_${PN} += "wpeframework-ui"

SRC_URI = "git://github.com/rdkcentral/ThunderUI.git;protocol=https"

# R4.3 of ThunderUI project
SRCREV = "aa8c9287cc34753d66eb9a2fd4c114d7dda78a19"

S = "${WORKDIR}/git"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
	rm -rf ${D}${datadir}/WPEFramework/Controller/UI/*
	mkdir -p ${D}${datadir}/WPEFramework/Controller/UI
	cp -r ${S}/src/* ${D}${datadir}/WPEFramework/Controller/UI
	cp -r ${S}/dist/* ${D}${datadir}/WPEFramework/Controller/UI
}

FILES_${PN} += "${datadir}/WPEFramework/Controller/UI/*"
