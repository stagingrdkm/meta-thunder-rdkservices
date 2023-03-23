FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# that patch should be upstreamed and removed after rdkservice OCDM implementation upstream and sync
# cannot be upstreamed right now - will cause compilation issues on remote repositories, and lack of some functionality on our side
SRC_URI += "file://0107-ONEM-25037-enable-stub-generation-for-Register-Unreg.patch"

SRC_URI_remove = "file://0001-RDK-31882-Add-GstCaps-parsing-in-OCDM-wpeframework-interfaces.patch"
SRC_URI += "file://0001-RDK-31882-Add-GstCaps-parsing-in-OCDM-wpeframework-interfaces-rdknext.patch"

SRC_URI += "file://0108-ARRISEOS-41993-Use-string-arguments-in-IBrowser-inte.patch"
#SRC_URI += "file://0109-ONEM-22741-lisa-locking.patch" # need to rebase and apply
SRC_URI += "file://0110-OMWAPPI-910-Increase-subsamples-limit-for-4k-content.patch"
SRC_URI += "file://0001-OMWAPPI-1315-Extend-ocdm-rdkservice-for-metrics-API.patch"
SRC_URI += "file://0111-ONEM-29742-setParameter-for-ISession-R4.patch"
SRC_URI += "file://0112-OMWAPPI-1314-Extend-ThunderInterfaces-ocdm-API-R4.patch"

require wpeframework-interfaces/interfaces.inc

# R4/ R4.1 tag
SRCREV_wpeframework-interfaces = "5011063f484e3222da06f46901f2845187a22653"

