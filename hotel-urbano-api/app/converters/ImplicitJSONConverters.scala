package converters

import org.joda.time.format.DateTimeFormat
import org.joda.time.LocalDate

object ImplicitBSONConverters {

  def parseDate(date: String): LocalDate = {
    val ft = DateTimeFormat.forPattern("dd/MM/yyyy")
    LocalDate.parse(date, ft)
  }

  def getDaysStream(checkin: String, checkout: String): Stream[LocalDate] = {
    val checkinDate  = parseDate(checkin)
    val checkoutDate = parseDate(checkout)
    dayIterator(checkinDate, checkoutDate)
  }

  def dayIterator(start: LocalDate, end: LocalDate) = {
    Stream.iterate(start)(_ plusDays 1) takeWhile (_ isBefore end.plusDays(1))
  }
}

