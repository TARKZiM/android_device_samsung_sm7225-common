/*===-- idiv0.c - 32-bit unsigned integer divide --------------------------===//
 *
 *                     The LLVM Compiler Infrastructure
 *
 * This file is dual licensed under the MIT and the University of Illinois Open
 * Source Licenses. See LICENSE.TXT for details.
 *
 *===----------------------------------------------------------------------===//
 *
 * This file implements the __aeabi_idiv0 and __aeabi_uidiv0
 * function for the ARM 32-bit architecture.
 *
 *===----------------------------------------------------------------------===*/
#include "idiv0.h"

#ifdef __ARM_EABI__
extern int __aeabi_idiv0(void) {
  return raise(SIGFPE);
}
extern int __aeabi_uidiv0(void) {
  return raise(SIGFPE);
}
extern "C" uint64_t __aeabi_uidiv(uint64_t numerator, uint64_t denominator) {
    if (denominator == 0) {
        return 0;
    }
    return numerator / denominator;
}
extern "C" int __aeabi_idiv(int numerator, int denominator) {
    if (denominator == 0) return 0;
    return numerator / denominator;
}

extern "C" uint64_t __aeabi_uidivmod(uint64_t numerator, uint64_t denominator) {
    if (denominator == 0) return 0;
    return numerator % denominator;
}
extern "C" int __srget(FILE *stream) {
    return 0;
}
#endif
