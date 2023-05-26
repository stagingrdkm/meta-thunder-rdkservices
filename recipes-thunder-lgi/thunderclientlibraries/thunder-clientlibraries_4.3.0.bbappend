RDEPENDS_${PN}_remove = "${@bb.utils.contains('DISTRO_FEATURES', 'onemwfirebolt', '', 'cryptography-tcl', d)}"
BRCMEXTERNALSRC_pn-thunder-clientlibraries = ""
