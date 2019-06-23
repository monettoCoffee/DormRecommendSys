# coding=utf-8


def vector_normalization(vector):
    max_element = -1
    for number in vector:
        max_element = max(max_element, number)
    vector_index = len(vector) - 1
    while vector_index > -1:
        vector[vector_index] = vector[vector_index] / max_element
        vector_index -= 1


if __name__ == "__main__":
    vector_list = [1, 2, 3, 4]
    vector_normalization(vector_list)
    for element in vector_list:
        print(element)
