import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'truncate'
})
export class TruncatePipe implements PipeTransform {
  transform(value: string, limit: number = 20, ellipsis: string = '...'): string {
    if (value.length > limit) {
      return value.slice(0, limit) + ellipsis;
    }
    return value;
  }
}
