package converters

import org.joda.time.format.DateTimeFormat
import org.joda.time.LocalDate

object ImplicitBSONConverters {


  def getDaysStream(checkin: String, checkout: String): Stream[LocalDate] = {
    val ft = DateTimeFormat.forPattern("dd/MM/yyyy")
    val checkinDate = LocalDate.parse(checkin, ft)
    val checkoutDate= LocalDate.parse(checkout, ft)
    dayIterator(checkinDate, checkoutDate)
  }

  def dayIterator(start: LocalDate, end: LocalDate) = Stream.iterate(start)(_ plusDays 1) takeWhile (_ isBefore end)

}
