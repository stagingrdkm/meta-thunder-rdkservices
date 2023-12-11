find_package(DBus REQUIRED)
find_package(Threads REQUIRED)
find_package(DS REQUIRED)

set(PUBLIC_HEADERS
        DSManagerPlugin.h
)

set(SOURCES
        DSManagerPlugin.cpp
        systemddbusclient.cpp
        dbuslistener.cpp
)

set(TARGET ${NAMESPACE}DSManagerPlugin)

add_library(${TARGET} SHARED ${SOURCES})

include_directories(${DBUS_INCLUDE_DIRS} ${DS_INCLUDE_DIRS})
target_compile_definitions(${TARGET} PRIVATE MESSAGING_EXPORTS)

target_link_libraries(${TARGET}
        PRIVATE
        ${DBUS_LIBRARY}
        ${DS_LIBRARY}
        )

set_target_properties(${TARGET} PROPERTIES
        CXX_STANDARD 11
        CXX_STANDARD_REQUIRED YES
        FRAMEWORK FALSE
        PUBLIC_HEADER "${PUBLIC_HEADERS}" # specify the public headers
        VERSION ${VERSION}
        SOVERSION ${VERSION_MAJOR}
        )

target_include_directories( ${TARGET}
        PUBLIC
          $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}>
          $<BUILD_INTERFACE:${CMAKE_CURRENT_SOURCE_DIR}/../>
          $<INSTALL_INTERFACE:include/${NAMESPACE}>
        )

install(
        TARGETS ${TARGET}  EXPORT ${TARGET}Targets  # for downstream dependencies
        ARCHIVE DESTINATION lib COMPONENT libs      # static lib
        LIBRARY DESTINATION lib COMPONENT libs      # shared lib
        RUNTIME DESTINATION bin COMPONENT libs      # binaries
        FRAMEWORK DESTINATION bin COMPONENT libs    # for mac
        PUBLIC_HEADER DESTINATION include/${NAMESPACE}/dsmanagerplugin COMPONENT devel   # headers for mac (note the different component -> different package)
        INCLUDES DESTINATION include/${NAMESPACE}   # default include path
)

InstallCMakeConfig(TARGETS ${TARGET})






