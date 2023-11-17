package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MergeSort<T> {

    private final Comparator<T> comparator;

    public MergeSort(Comparator<T> comparator) { // Setting custom comparator
        this.comparator = comparator;
    }

    public ObservableList<T> sort(ObservableList<T> inputList) {
        if (inputList == null || inputList.size() <= 1) {
            return inputList;
        }

        int middle = inputList.size() / 2;

        ObservableList<T> left = sort(FXCollections.observableArrayList(inputList.subList(0, middle)));
        ObservableList<T> right = sort(FXCollections.observableArrayList(inputList.subList(middle, inputList.size())));

        return merge(left, right);
    }

    private ObservableList<T> merge(ObservableList<T> left, ObservableList<T> right) {
        ObservableList<T> result = FXCollections.observableArrayList();
        int leftIndex = 0, rightIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (comparator.compare(left.get(leftIndex), right.get(rightIndex)) <= 0) {
                result.add(left.get(leftIndex));
                leftIndex++;
            } else {
                result.add(right.get(rightIndex));
                rightIndex++;
            }
        }

        // Copy the remaining elements of left and right, if any
        result.addAll(FXCollections.observableArrayList(left.subList(leftIndex, left.size())));
        result.addAll(FXCollections.observableArrayList(right.subList(rightIndex, right.size())));

        return result;
    }
}
