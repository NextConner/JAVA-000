from faker import Faker
fake = Faker()

with open("data.txt","a") as file:
    i=1000000
    while i > 0 :
        file.write(fake.bothify(text='?????????-############', letters='ABCDEFGHI'))
        file.write(fake.bothify(text='?????????-############', letters='ABCDEFGHI'))
        file.write(fake.bothify(text='#####-############'))
        file.write("")
        file.write(faker.providers.phone_number)
        file.write(faker.providers.phone_number)
        file.write(faker.providers.phone_number)
        file.write(faker.providers.phone_number)
        file.write(faker.providers.phone_number)
        file.write(faker.providers.phone_number)
        file.write(faker.providers.phone_number)
        file.write(faker.providers.phone_number)
        file.write(faker.providers.phone_number)
        file.write(faker.providers.phone_number)
        file.write(faker.providers.phone_number)
        file.write(faker.providers.phone_number)