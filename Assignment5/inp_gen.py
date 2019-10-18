"""
Prints a certain number of jobs with time 10
"""
import random

print('''USER Rob
USER Harry
USER Carry
PROJECT IITD.CS.ML.ICML 10 1
PROJECT IITD.CS.OS.ASPLOS 9 1
PROJECT IITD.CS.TH.SODA 8 1''')

PROJECTS = [
    "IITD.CS.ML.ICML",
    "IITD.CS.OS.ASPLOS",
    "IITD.CS.TH.SODA"
]

USERS = [
    "Rob",
    "Harry",
    "Carry"
]

JOBS = []
with open("test-names.txt") as f:
    for s in f:
        JOBS.append(s.split()[0])

random.shuffle(JOBS)
for job in JOBS:
    project = random.choice(PROJECTS)
    user = random.choice(USERS)
    print(f"JOB {job} {project} {user} 10")

print()

print("NEW_PRIORITY 10")