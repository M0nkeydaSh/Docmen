package ru.imsit.diplom.docmen.enums;

import java.util.stream.Stream;

public enum GenderEnum {
    MAN("М"),
    WOMAN("Ж");

    GenderEnum(String gender) {
    }

    public static GenderEnum getGender(String gender) {
        if (gender != null) {
            return Stream.of(GenderEnum.values())
                    .filter(genderEnum -> genderEnum.name().equals(gender))
                    .findFirst()
                    .orElse(GenderEnum.MAN);
        } else {
            return GenderEnum.MAN;
        }
    }
}
