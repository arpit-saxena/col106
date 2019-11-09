N=int(input())

p1=0
p2=1
for i in range(N//2):
	print("ADD_TRIANGLE",p1,0,0,p2,0,0,p1,1,0)
	print("ADD_TRIANGLE",p2,0,0,p1,1,0,p2,1,0)
	p1+=1
	p2+=1
print("MAXIMUM_DIAMETER")
""" print("CLOSEST_COMPONENTS") """
p11=0
p12=0
p13=0
p21=-1.25
p22=0
p23=0
p31=0
p32=-1.25
p33=0
for i in range(N):
	p11-=1.25
	p21-=1.25
	p32-=1.25
	print("ADD_TRIANGLE",p11,p12,p13,p21,p22,p23,p31,p32,p33)
	

print("COUNT_CONNECTED_COMPONENTS")
print("TYPE_MESH")
print("IS_CONNECTED",p11,p12,p13,p21,p22,p23,p31,p32,p33,-1.25*(N//5),0,0,-1.25*((N//5)+1),0,0,0,-1.25*((N//5)+1),0)
print("IS_CONNECTED",p11,p12,p13,p21,p22,p23,p31,p32,p33,0,0,0,1,0,0,0,1,0)
print("CENTROID")
print("ADD_TRIANGLE -1.25 0 0 -2.50 0 0 0 1.25 0")
print("TYPE_MESH")
print("CLOSEST_COMPONENTS")
print("CENTROID_OF_COMPONENT 0 1.25 0")
print("MAXIMUM_DIAMETER")
print("BOUNDARY_EDGES")
	
