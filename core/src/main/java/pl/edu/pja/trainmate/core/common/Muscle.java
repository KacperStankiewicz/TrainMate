package pl.edu.pja.trainmate.core.common;

import static pl.edu.pja.trainmate.core.common.Muscle.MuscleGroup.ARMS;
import static pl.edu.pja.trainmate.core.common.Muscle.MuscleGroup.BACK;
import static pl.edu.pja.trainmate.core.common.Muscle.MuscleGroup.CHEST;
import static pl.edu.pja.trainmate.core.common.Muscle.MuscleGroup.LEGS;
import static pl.edu.pja.trainmate.core.common.Muscle.MuscleGroup.SHOULDERS;
import static pl.edu.pja.trainmate.core.common.Muscle.MuscleGroup.STOMACH;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Muscle {

    BICEPS(ARMS),
    TRICEPS(ARMS),
    FOREARM(ARMS),

    FRONTAL_DELTOID(SHOULDERS),
    MIDDLE_DELTOID(SHOULDERS),
    REAR_DELTOID(SHOULDERS),

    UPPER_CHEST(CHEST),
    MIDDLE_CHEST(CHEST),
    LOWER_CHEST(CHEST),

    LATS(BACK),
    RHOMBOID(BACK),
    DELTOID(BACK),
    TRAPEZIUS(BACK),
    LOWER_BACK(BACK),

    HAMSTRINGS(LEGS),
    GLUTES(LEGS),
    CALVES(LEGS),
    QUADS(LEGS),

    OBLIQUE(STOMACH),
    ABS(STOMACH);

    private final MuscleGroup muscleGroup;

    Muscle(MuscleGroup muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public boolean isInGroup(MuscleGroup group) {
        return this.muscleGroup.equals(group);
    }

    public static List<Muscle> getAllMusclesByGroup(MuscleGroup group) {
        return Arrays.stream(Muscle.values()).filter(it -> it.isInGroup(group)).collect(Collectors.toList());
    }

    public enum MuscleGroup {
        ARMS,
        SHOULDERS,
        CHEST,
        BACK,
        LEGS,
        STOMACH
    }

}
