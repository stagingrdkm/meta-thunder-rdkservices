FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

# that patch should be upstreamed and removed after rdkservice OCDM implementation upstream and sync
# cannot be upstreamed right now - will cause compilation issues on remote repositories, and lack of some functionality on our side
SRC_URI += "file://0107-ONEM-25037-enable-stub-generation-for-Register-Unreg.patch"

# we need this
#SRC_URI += "file://0001-RDK-31882-Add-GstCaps-parsing-in-OCDM-wpeframework-interfaces-rdknext.patch"
SRC_URI += "file://0108-ARRISEOS-41993-Use-string-arguments-in-IBrowser-inte.patch"
SRC_URI += "file://0110-OMWAPPI-910-Increase-subsamples-limit-for-4k-content.patch"
SRC_URI += "file://0111-ONEM-29742-setParameter-for-ISession.patch"
SRC_URI += "file://0007-ONEM-24354-Clear-content-of-buffers-on-deinit.patch"

require thunder-interfaces-4.3.0/interfaces.inc
