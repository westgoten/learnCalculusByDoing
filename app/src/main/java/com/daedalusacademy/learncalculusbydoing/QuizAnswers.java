package com.daedalusacademy.learncalculusbydoing;

import android.util.SparseArray;

public class QuizAnswers {
    private static final SparseArray<boolean[]> objectiveAnswers = new SparseArray<>();
    private static final SparseArray<String> textAnswers = new SparseArray<>();

    static {
        objectiveAnswers.append(0, new boolean[]{false, false, true, true});
        objectiveAnswers.append(1, new boolean[]{false, true, false, true});
    }

    public static boolean[] getObjectiveAnswer(int questionNumber) {
        return objectiveAnswers.get(questionNumber);
    }

    public static String getTextAnswer(int questionNumber) {
        return textAnswers.get(questionNumber);
    }
}
