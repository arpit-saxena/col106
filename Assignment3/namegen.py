from sys import stdin

names = []
count = 0;
for line in stdin:
    names.append(line.rstrip())
    count += 1
    if count == 300:
        break

for name1 in names:
    for name2 in names:
        print(f'insert {name1} {name2} Nilgiri CS 6.5')
