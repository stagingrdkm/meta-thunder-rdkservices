#ifndef LOG_H
#define LOG_H

#include <cstdio>

#define LOG_MACRO(log_level, fmt, ...) \
    do { \
        printf("DSManagerPlugin [%s] -> ", log_level); \
        printf(fmt, ##__VA_ARGS__); \
        printf(" | %s (%s:%d)\n", __PRETTY_FUNCTION__, __FILE__, __LINE__); \
        fflush(stdout); \
    } while (0)

#define LOGINF(fmt, ...) LOG_MACRO("INF", fmt, ##__VA_ARGS__)
#define LOGERR(fmt, ...) LOG_MACRO("ERR", fmt, ##__VA_ARGS__)
#define LOGDBG(fmt, ...) LOG_MACRO("DBG", fmt, ##__VA_ARGS__)

#endif //LOG_H
