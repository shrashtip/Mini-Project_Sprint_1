use PlayStore;
CREATE TABLE apps (
    App_name VARCHAR(255) NOT NULL,
    description TEXT,
    release_date DATE,
    version VARCHAR(20),
    ratings DECIMAL(3, 1),
    category VARCHAR(20),
    visible BOOLEAN
);
  
USE PlayStore;

-- Insert new records with updated values:
INSERT INTO apps (App_name, description, release_date, version, ratings, category, visible) 
VALUES 
('WhatsApp', 'A messaging app for text, voice, and video communication.', '2009-05-03', '4.0.0', 4.6, 'Communication', true),
('Instagram', 'A social media platform for sharing photos and videos.', '2010-10-06', '150.0.0', 4.7, 'Social', true),
('TikTok', 'A video-sharing social networking service.', '2016-09-17', '18.0.0', 4.4, 'Social', true),
('Zoom', 'A video conferencing software.', '2012-04-21', '5.0.0', 4.5, 'Communication', true),
('Netflix', 'A subscription-based streaming service for movies and TV shows.', '2007-08-29', '7.0.0', 4.8, 'Entertainment', true),
('Spotify', 'A digital music service that gives users access to millions of songs.', '2008-04-23', '10.0.0', 4.7, 'Music', true),
('Google Maps', 'A web mapping service for real-time GPS navigation, traffic, and more.', '2005-02-08', '10.0.0', 4.6, 'Maps & Navigation', true),
('Facebook', 'A social networking service.', '2004-02-04', '300.0.0', 4.5, 'Social', true),
('Snapchat', 'A multimedia messaging app.', '2011-09-26', '12.0.0', 4.3, 'Social', true),
('Twitter', 'A microblogging and social networking service.', '2006-07-15', '8.0.0', 4.2, 'Social', true),
('Amazon', 'An e-commerce platform offering various products and services.', '1994-07-05', '15.0.0', 4.6, 'Shopping', true),
('Uber', 'A ride-sharing and transportation service.', '2009-03-01', '7.0.0', 4.5, 'Travel & Local', true),
('Google Drive', 'A file storage and synchronization service.', '2012-04-24', '5.0.0', 4.6, 'Productivity', true),
('WhatsApp Business', 'A business version of the popular messaging app WhatsApp.', '2018-01-18', '2.0.0', 4.7, 'Business', true),
('Skype', 'A telecommunications application providing video chat and voice calls.', '2003-08-29', '8.0.0', 4.4, 'Communication', true),
('LinkedIn', 'A professional networking platform.', '2002-12-14', '15.0.0', 4.5, 'Business', true),
('Microsoft Teams', 'A platform that combines workplace chat, meetings, and collaboration.', '2017-11-02', '5.0.0', 4.6, 'Business', true),
('Pinterest', 'A social media platform for discovering and saving ideas.', '2010-12-20', '10.0.0', 4.7, 'Social', true),
('Google Photos', 'A photo sharing and storage service developed by Google.', '2015-05-28', '5.0.0', 4.6, 'Photography', true),
('Amazon Prime Video', 'A video-on-demand service.', '2006-09-07', '10.0.0', 4.5, 'Entertainment', true),
('Microsoft Word', 'A word processor developed by Microsoft.', '1983-10-25', '15.0.0', 4.7, 'Productivity', true),
('Adobe Photoshop', 'A raster graphics editor developed by Adobe Inc.', '1988-02-19', '25.0.0', 4.8, 'Photography', true),
('Google Translate', 'A multilingual neural machine translation service.', '2006-04-28', '10.0.0', 4.5, 'Tools', true),
('Microsoft Excel', 'A spreadsheet developed by Microsoft.', '1987-09-30', '15.0.0', 4.6, 'Productivity', true),
('YouTube', 'A video-sharing platform.', '2005-02-14', '20.0.0', 4.8, 'Entertainment', true),
('Adobe Acrobat Reader', 'A free global standard for reliably viewing, printing, and commenting on PDF documents.', '1993-06-15', '20.0.0', 4.7, 'Productivity', true),
('Google Chrome', 'A web browser developed by Google.', '2008-09-02', '100.0.0', 4.6, 'Tools', true),
('Microsoft PowerPoint', 'A presentation program developed by Microsoft.', '1990-05-22', '15.0.0', 4.5, 'Productivity', true),
('Discord', 'A VoIP, instant messaging, and digital distribution platform.', '2015-05-13', '15.0.0', 4.6, 'Communication', true),
('Telegram', 'A cloud-based instant messaging and voice over IP service.', '2013-08-14', '7.0.0', 4.7, 'Communication', true);

-- Display the updated table:
SELECT * FROM apps;
