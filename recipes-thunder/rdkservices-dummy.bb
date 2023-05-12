SUMMARY = "RDK services plugins - dummy receipe - we have multiple dependencies in different places for that"
LICENSE = "Apache-2.0"
PROVIDES += "rdkservices"
RPROVIDES_${PN} += "rdkservices"

LICENSE = "CLOSED"
SRC_URI = "file://thunder_acl.json"

do_install_append() {
    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/thunder_acl.json ${D}${sysconfdir}
}
