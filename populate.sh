URL_CITIES="http://www.mysite.com/api/cities"
URL_HOTELS="http://www.mysite.com/api/hotels"

cityIds=("a4065490-8cda-45e4-b42c-ff8f7f792ef4" "c7e6baaf-2812-4e3b-8f29-1c489ceadc15")
cityNames=("Rio de Janeiro, Brasil" "Fortaleza, Brasil")

hotelRIds=("44eeb72e-6df0-487c-8462-a75fa9c670d1" "3e252daa-5e71-4ca4-87b3-365aa30b93d9" "2bd49162-172e-4ad0-b375-424a808172ce")
hotelRNames=("Vila Galé Rio de Janeiro" "Copacabana Rio Hotel" "Royal Tulip Rio de Janeiro")

hotelFIds=("51075f93-8ccf-4390-bc17-a370bdf7dbf4" "a4cbeb4a-40d9-4ab0-ae07-abd49de5ba3a" "90675a1e-8340-4acc-9dc5-c691bbf61020")
hotelFNames=("Fortaleza Park Hotel" "Dunas Hotel" "Iracema Hotel")

# Cities
for ((i=0; i < ${#cityIds[@]}; i++))
do
  curl -H "Content-Type: application/json" -d "{\"cityId\":\"${cityIds[$i]}\",\"cityName\":\"${cityNames[$i]}\"}" $URL_CITIES
done

# Rio Hotels
for ((i=0; i < ${#hotelRIds[@]}; i++))
do
  curl -H "Content-Type: application/json" -d "{\"hotelId\":\"${hotelRIds[$i]}\",\"hotelName\":\"${hotelRNames[$i]}\",\"hotelPrice\":\"199.99\",\"cityId\":\"a4065490-8cda-45e4-b42c-ff8f7f792ef4\",\"dates\":[]}" $URL_HOTELS
done

# Fortaleza Hotels
for ((i=0; i < ${#hotelFIds[@]}; i++))
do
  curl -H "Content-Type: application/json" -d "{\"hotelId\":\"${hotelFIds[$i]}\",\"hotelName\":\"${hotelFNames[$i]}\",\"hotelPrice\":\"199.99\",\"cityId\":\"c7e6baaf-2812-4e3b-8f29-1c489ceadc15\",\"dates\":[]}" $URL_HOTELS
done

