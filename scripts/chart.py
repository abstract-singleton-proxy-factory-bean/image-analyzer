import matplotlib.pyplot as plt
import json, sys
from pathlib import Path


def map(source: list, callback) -> list:
    return [callback(item) for item in source]


def get_data(filename: str) -> dict:
    with open(filename, 'r') as file:
        return json.loads(''.join(file.readlines()))


if __name__ == '__main__':
    filename = Path(sys.argv[1])

    if not filename:
        exit(1)

    data = get_data(filename)
    fig, ax = plt.subplots()
    ax.plot(
        map(data, lambda item: item['year']),
        map(data, lambda item: item['percentage']),
    )

    ax.set_title('Percentage of green area in the south of Amazonas')
    ax.set_ylabel('Percentage of green area')
    ax.set_xlabel('Year')

    plt.savefig('chart.png')
