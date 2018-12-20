package com.westgoten.learncalculusbydoing;

import android.util.SparseArray;

public class QuizAnswers {
    private static final SparseArray<boolean[]> objectiveAnswers = new SparseArray<>();
    private static final SparseArray<String> textAnswers = new SparseArray<>();

    static {
        objectiveAnswers.append(0, new boolean[]{false, false, true, true});
        objectiveAnswers.append(1, new boolean[]{false, true, false, true});
        objectiveAnswers.append(2, new boolean[]{true, true, true, false});
        objectiveAnswers.append(3, new boolean[]{true, false, false, true});
        objectiveAnswers.append(4, new boolean[]{false, false, false, true});
        objectiveAnswers.append(5, new boolean[]{false, false, true, false});
        objectiveAnswers.append(6, new boolean[]{false, false, false, true});
        textAnswers.append(7, "y");
        textAnswers.append(8, "2");
        textAnswers.append(9, "9");
    }

    public static boolean[] getObjectiveAnswer(int questionNumber) {
        return objectiveAnswers.get(questionNumber);
    }

    public static String getTextAnswer(int questionNumber) {
        return textAnswers.get(questionNumber);
    }
}
