#ifndef INIT_SEC_H
#define INIT_SEC_H

#include <string.h>

enum device_variant {
    VARIANT_A426B = 0,
    VARIANT_A426N,
    VARIANT_A426U,
    VARIANT_MAX
};

typedef struct {
    std::string model;
    std::string codename;
} variant;

static const variant international_models = {
    .model = "SM-A426B",
    .codename = "a42xq"
};

static const variant kor_models = {
    .model = "SM-A426N",
    .codename = "a42xq"
};

static const variant america_models = {
    .model = "SM-A426U",
    .codename = "a42xq"
};

static const variant *all_variants[VARIANT_MAX] = {
    &international_models,
    &kor_models,
    &america_models,
};

#endif // INIT_SEC_H
