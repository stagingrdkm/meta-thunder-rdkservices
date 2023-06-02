# Example how to cherry-picks unmerged changes from RDK gerrit and use them in our CI.
# The following example fetches the code from:
# https://code.rdkcentral.com/r/c/apps/netflix/netflix-6.1/+/79896/8
inherit codecentral_cherry_picking
CODECENTRAL_CHERRY_PICKS += "85607/revisions/439980100bc5e74b5b91a5444c4d0c2902a3c8f7 import-sources.patch;patchdir=${S}/../../../git/"

