package youtube;

public class Format {
    public int id;
    public String extension;
    public String size;
    public String bitrate;
    public Boolean audioOnly;
    public Boolean videoOnly;
    public Boolean hdr;
    public int width;
    public int height;

    public Format() {
        id = 0;
        extension = "";
        size = "";
        bitrate = "";
        audioOnly = false;
        videoOnly = false;
        hdr = false;
        width = 0;
        height = 0;
    }

    public Format(int id, String extension, Boolean audioOnly, String size) {
        this.id = id;
        this.extension = extension;
        this.audioOnly = audioOnly;
        this.size = size;
        this.videoOnly = false;
    }

    public Format(String formatString) {
        this.videoOnly = false;
        this.audioOnly = false;
        this.hdr = false;
        this.setId(Integer.parseInt(formatString.substring(0, formatString.indexOf(" "))));
        String audioVideo = formatString.substring(24);
        audioVideo = audioVideo.substring(0, audioVideo.indexOf(" "));
        if (audioVideo.equals("audio")) {
            this.setAudioOnly(true);
        } else {
            int xPos = audioVideo.indexOf("x");
            int width = Integer.parseInt(audioVideo.substring(0, xPos).toString());
            int height = Integer.parseInt(audioVideo.substring(xPos + 1).toString());
            this.setWidth(width);
            this.setHeight(height);
            this.videoOnly = formatString.contains("video only");
            this.hdr = formatString.contains("HDR");
        }
        this.setExtension(formatString.substring(13, 17));

    }

    public String toString() {
        String output = "";
        if (this.audioOnly) {
            output += "AUDIO ";
        } else {
            if (this.videoOnly) {
                output += "VIDEO ";
            }
            output += this.width + "X" + this.height;
        }
        output = output + " " + this.extension;
        output += hdr?" HDR":"";
        return output;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the extension
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @param extension the extension to set
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * @return the size
     */
    public String getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * @return the bitrate
     */
    public String getBitrate() {
        return bitrate;
    }

    /**
     * @param bitrate the bitrate to set
     */
    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }

    /**
     * @return the audioOnly
     */
    public Boolean getAudioOnly() {
        return audioOnly;
    }

    /**
     * @param audioOnly the audioOnly to set
     */
    public void setAudioOnly(Boolean audioOnly) {
        this.audioOnly = audioOnly;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

}
