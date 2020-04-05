package org.telbots;

public enum SymbolsAndEmojis {

    BACK_BUTTON('\uD83D', '\uDD19'),
    HI(null, '\u270B'),
    GRAPH('\uD83D', '\uDCC8'),
    COMPUTER('\uD83D','\uDCBB'),
    ARTICLES('\uD83D','\uDCCE');

    Character char1;
    Character char2;
    SymbolsAndEmojis(Character char1, Character char2) {
        this.char1 = char1;
        this.char2 = char2;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if(this.char1!=null)
        stringBuilder.append(char1);
        if(this.char2!=null)
        stringBuilder.append(char2);
        return stringBuilder.toString();
    }
}
