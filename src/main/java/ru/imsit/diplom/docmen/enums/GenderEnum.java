package ru.imsit.diplom.docmen.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum GenderEnum {
    M("М"),
    W("Ж");

    private final String gender;

    public static GenderEnum getGender(String gender) {
        if (gender != null) {
            return Stream.of(GenderEnum.values())
                    .filter(genderEnum -> genderEnum.gender.equals(gender))
                    .findFirst()
                    .orElse(GenderEnum.M);
        } else {
            return GenderEnum.M;
        }
    }

    @Override
    public String toString() {
        return gender;
    }
}
