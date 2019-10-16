from sys import stdin

names = []
count = 0;
for line in stdin:
    names.append(line.rstrip())
    count += 1
    if count == 316:
        break

for name1 in names:
    for name2 in names:
        print(f'INSERT\n{name1} {name2}, Nilgiri')
