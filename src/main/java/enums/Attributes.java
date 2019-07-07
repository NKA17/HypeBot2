package enums;

/**
 * You could probably figure out what these are for
 */
public enum Attributes {
    CUSTOM, //Tag onto user made actions
    BUILT_IN, //Tag onto Developer made actions
    VANILLA, //Tag onto actions that come in original bot
    SEND, //Action that responds to messages sent in chat
    EXECUTE, //Action edits local runtime variables
    ALIAS, //Replaces text in SEND action responses
    MEME, //Dedicated for sending captioned images
    ACTION, //All actions have this
    ;
}
