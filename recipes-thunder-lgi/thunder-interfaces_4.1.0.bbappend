FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

# that patch should be upstreamed and removed after rdkservice OCDM implementation upstream and sync
# cannot be upstreamed right now - will cause compilation issues on remote repositories, and lack of some functionality on our side
SRC_URI += "file://0107-ONEM-25037-enable-stub-generation-for-Register-Unreg.patch"

SRC_URI_remove = "file://file://0001-Revert-Merge-pull-request-137-from-mikolaj-staworzyn.patch"

SRC_URI += "file://0001-RDK-31882-Add-GstCaps-parsing-in-OCDM-wpeframework-interfaces-rdknext.patch"

SRC_URI += "file://0108-ARRISEOS-41993-Use-string-arguments-in-IBrowser-inte.patch"
#SRC_URI += "file://0109-ONEM-22741-lisa-locking.patch" # need to rebase and apply
SRC_URI += "file://0110-OMWAPPI-910-Increase-subsamples-limit-for-4k-content.patch"
SRC_URI += "file://0001-OMWAPPI-1315-Extend-ocdm-rdkservice-for-metrics-API.patch"
SRC_URI += "file://0111-ONEM-29742-setParameter-for-ISession.patch"
SRC_URI += "file://0112-OMWAPPI-1314-Extend-ThunderInterfaces-ocdm-API.patch"

require thunder-interfaces-4.1.0/interfaces.inc
