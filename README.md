# Schedule Poster
### App for hackathon that posts TV schedule to VK group  
  
To use program:  
- generate **vk user token** by tutorial [here](https://vk.com/dev/implicit_flow_user) (you should have **wall** and **photos** scope);
- insert token and group id (to which you'd like to post) to corresponding fields in [vk.properties](../master/src/main/resources/vk.properties) file.
  
You can customize program by changing values in [.properties](../master/src/main/resources/) files:  
- in [vk.properties](../master/src/main/resources/vk.properties) change group to post to and message that will be posted along with picture;
- in [ovva.properties](../master/src/main/resources/ovva.properties) change channel name and language of schedule (make sure that you insert valid values, see what's available [here](https://api.ovva.tv/);
- in [image.properties](../master/src/main/resources/image.properties) change settings of the image with schedule - from background color to font family and format of program time.

Use with caution! (:
