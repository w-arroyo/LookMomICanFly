export class DateFormatter{

    static formatDate(date:Date){
        const formatter = new Intl.DateTimeFormat('en-US', {
            day: 'numeric',
            month: 'long',
            year: 'numeric',
            });
        return formatter.format(date);
    }

}